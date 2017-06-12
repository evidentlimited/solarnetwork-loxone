/* ==================================================================
 * BaseConfigurationEntityDao.java - 19/09/2016 4:53:25 PM
 * 
 * Copyright 2007-2016 SolarNetwork.net Dev Team
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 * 02111-1307 USA
 * ==================================================================
 */

package net.solarnetwork.node.loxone.dao.jdbc;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import net.solarnetwork.domain.SortDescriptor;
import net.solarnetwork.node.loxone.dao.ConfigurationEntityDao;
import net.solarnetwork.node.loxone.domain.BaseConfigurationEntity;

/**
 * Base DAO for {@link BaseConfigurationEntity} classes.
 * 
 * @author matt
 * @version 1.1
 */
public abstract class BaseConfigurationEntityDao<T extends BaseConfigurationEntity>
		extends BaseUUIDEntityDao<T> implements ConfigurationEntityDao<T> {

	/**
	 * SQL resource to find by name. Accepts a {@code configId} and
	 * {@code name}.
	 */
	public static final String SQL_FIND_FOR_NAME = "find-for-name";

	/**
	 * Init with an an entity name and table version, deriving various names
	 * based on conventions.
	 * 
	 * @param entityClass
	 *        The class of the entity managed by this DAO.
	 * @param entityName
	 *        The entity name to use. This name forms the basis of the default
	 *        SQL resource prefix, table name, tables version query, and SQL
	 *        init resource.
	 * @param version
	 *        The tables version.
	 * @param rowMapper
	 *        A row mapper to use when mapping entity query results.
	 */
	public BaseConfigurationEntityDao(Class<T> entityClass, String entityName, int version,
			RowMapper<T> rowMapper) {
		super(entityClass, entityName, version, rowMapper);
	}

	/**
	 * Init with an an entity name and table version, deriving various names
	 * based on conventions.
	 * 
	 * @param sqlResourcePrefixTemplate
	 *        a template with a single {@code %s} parameter for the SQL resource
	 *        prefix
	 * @param tableNameTemplate
	 *        a template with a single {@code %s} parameter for the SQL table
	 *        name
	 * @param entityClass
	 *        The class of the entity managed by this DAO.
	 * @param entityName
	 *        The entity name to use. This name forms the basis of the default
	 *        SQL resource prefix, table name, tables version query, and SQL
	 *        init resource.
	 * @param version
	 *        The tables version.
	 * @param rowMapper
	 *        A row mapper to use when mapping entity query results.
	 */
	public BaseConfigurationEntityDao(String sqlResourcePrefixTemplate, String tableNameTemplate,
			Class<T> entityClass, String entityName, int version, RowMapper<T> rowMapper) {
		super(sqlResourcePrefixTemplate, tableNameTemplate, entityClass, entityName, version, rowMapper);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void store(T entity) {
		storeEntity(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public T load(Long configId, UUID uuid) {
		return getEntityByUUID(configId, uuid);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public int deleteAllForConfig(Long configId) {
		return deleteAllEntitiesForConfig(configId);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<T> findAllForConfig(Long configId, List<SortDescriptor> sortDescriptors) {
		return findAllEntitiesForConfig(configId, sortDescriptors);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<T> findAllForConfigAndName(Long configId, String name,
			List<SortDescriptor> sortDescriptors) {
		String sql = getSqlResource(SQL_FIND_FOR_NAME);
		sql = handleSortDescriptors(sql, sortDescriptors, sortDescriptorColumnMapping());
		List<T> results = getJdbcTemplate().query(sql, getRowMapper(), configId, name);
		return results;
	}

}
