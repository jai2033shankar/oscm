/*******************************************************************************
 *
 *  Copyright FUJITSU LIMITED 2016
 *
 *  Creation Date: 30.08.2016
 *
 *******************************************************************************/
package org.oscm.tenant.bean;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.oscm.dataservice.local.DataService;
import org.oscm.domobjects.Tenant;
import org.oscm.domobjects.TenantSetting;
import org.oscm.interceptor.InvocationDateContainer;
import org.oscm.internal.types.exception.NonUniqueBusinessKeyException;
import org.oscm.internal.types.exception.ObjectNotFoundException;
import org.oscm.tenant.dao.TenantDao;
import org.oscm.tenant.local.TenantServiceLocal;

@Interceptors({ InvocationDateContainer.class, org.oscm.interceptor.ExceptionMapper.class })
@Stateless
@Local(TenantServiceLocal.class)
public class TenantServiceLocalBean implements TenantServiceLocal {

    @EJB
    TenantDao tenantDao;

    @EJB
    DataService dataManager;

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @RolesAllowed("PLATFORM_OPERATOR")
    @Override
    public List<Tenant> getAllTenants() {
        return tenantDao.getAllTenants();
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @RolesAllowed("PLATFORM_OPERATOR")
    @Override
    public Tenant getTenantByTenantId(String tenantId) throws ObjectNotFoundException {
        return tenantDao.getTenantByTenantId(tenantId);
    }

    @Override
    public void saveTenant(Tenant tenant) throws NonUniqueBusinessKeyException {
        dataManager.persist(tenant);
    }

    @Override
    public Tenant getTenantByKey(long tkey) throws ObjectNotFoundException {
        return dataManager.getReference(Tenant.class, tkey);
    }

    @Override
    public void removeTenant(Tenant tenant) {
        dataManager.remove(tenant);
    }

    @Override
    public void saveTenantSetting(TenantSetting tenantSetting) throws NonUniqueBusinessKeyException {
        dataManager.persist(tenantSetting);
    }

    @Override
    public void removeTenantSetting(TenantSetting tenantSetting) throws ObjectNotFoundException {
        dataManager.remove(tenantSetting);
    }

    @Override
    public List<TenantSetting> getAllTenantSettingsForTenant(Tenant tenant) {
        return tenantDao.getAllTenantSettingsForTenant(tenant);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @Override
    public List<Tenant> getTenantsByIdPattern(String tenantIdPattern) {
        return tenantDao.getTenantsByIdPattern(tenantIdPattern);
    }

}
