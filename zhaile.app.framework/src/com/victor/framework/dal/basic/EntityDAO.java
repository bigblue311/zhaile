package com.victor.framework.dal.basic;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.LogTools;

public class EntityDAO<Entity extends EntityDO> {
	private static LogTools log = new LogTools(EntityDAO.class);
	
	private String namespace = "";
	private MySqlMapClient mySqlMapClient;
	
	public String getNamespace() {
		return namespace;
	}
	
	public void setMySqlMapClient(MySqlMapClient mySqlMapClient) {
		this.mySqlMapClient = mySqlMapClient;
	}

	public EntityDAO(String namespace){
		this.namespace = namespace;
	}
	
	public SqlMapClient getSqlMapClient(){
		return mySqlMapClient.getSqlMapClient();
	}
	
	public Result<List<Entity>> queryForList(String sqlId, Entity query){
		return queryForList(sqlId, query.toMap());
	}
	
	public Result<List<Object>> queryForList(String namespace, String sqlId, EntityDO query){
		return queryForList(namespace, sqlId, query.toMap());
	}
	
	public Result<List<Entity>> queryForList(String sqlId, String key, Object query){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId, query);
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<Entity> list = (List<Entity>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	public Result<List<Object>> queryForList(String namespace, String sqlId, String key, Object query){
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put(key, query);
		return queryForList(namespace,sqlId,queryMap);
	}
	
	public Result<List<Entity>> queryForList(String sqlId){
		return queryForList(sqlId,new HashMap<String,Object>());
	}
	
	@SuppressWarnings("unchecked")
	public Result<List<Object>> queryForList(String namespace, String sqlId){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId);
			if(obj != null) {
				List<Object> list = (List<Object>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result<List<Entity>> queryForList(String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId, query);
			if(obj != null) {
				List<Entity> list = (List<Entity>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result<List<Object>> queryForList(String namespace, String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForList(namespace+"."+sqlId, query);
			if(obj != null) {
				List<Object> list = (List<Object>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result<Entity> queryForEntity(String sqlId,Entity query){
		Result<Object> result = queryForEntity(this.namespace, sqlId,query.toMap());
		return Result.newInstance((Entity)result.getDataObject(), result.getMessage(), result.isSuccess());
	}
	
	public Result<Object> queryForEntity(String namespace, String sqlId,EntityDO query){
		return queryForEntity(namespace, sqlId, query.toMap());
	}
	
	public Result<Entity> queryForEntity(String sqlId, String key, Object query){
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put(key, query);
		return queryForEntity(sqlId,queryMap);
	}
	
	public Result<Object> queryForEntity(String namespace, String sqlId, String key, Object query){
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put(key, query);
		return queryForEntity(namespace,sqlId,queryMap);
	}
	
	@SuppressWarnings("unchecked")
	public Result<Entity> queryForEntity(String sqlId){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId);
			if(obj != null) {
				return Result.newInstance((Entity)obj, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	public Result<Object> queryForEntity(String namespace, String sqlId){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId);
			if(obj != null) {
				return Result.newInstance(obj, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result<Entity> queryForEntity(String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId, query);
			if(obj != null) {
				return Result.newInstance((Entity)obj, "获取分页数据成功", true);
			} else {
				return Result.newInstance(null, "获取分页数据为空", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Result.newInstance(null, "获取分页数据失败", false);
		}
	}
	
	public Result<Object> queryForEntity(String namespace, String sqlId, Map<String,Object> query){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+"."+sqlId, query);
			if(obj != null) {
				return Result.newInstance(obj, "获取分页数据成功", true);
			} else {
				return Result.newInstance(null, "获取分页数据为空", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Result.newInstance(null, "获取分页数据失败", false);
		}
	}

	public Result<Long> insert(Entity entity){
		return insert(this.namespace,entity);
	}
	
	public Result<Long> insert(String namespace, EntityDO entity){
		try {
			Long id = (Long)getSqlMapClient().insert(namespace+".insert",entity);
			return Result.newInstance(id, "插入数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", entity, e);
			return Result.newInstance(0l, "插入数据失败", false);
		}
	}
	
	public Result<Boolean> update(Entity entity){
		return update(this.namespace,entity);
	}
	
	public Result<Boolean> update(String sqlId){
		try {
			getSqlMapClient().update(this.namespace+"."+sqlId);
			return Result.newInstance(true, "更新数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", e);
			return Result.newInstance(false, "更新数据失败", false);
		}
	}
	
	public Result<Boolean> updateBySID(String sqlId,Entity entity){
		return updateBySID(this.namespace,sqlId,entity);
	}
	
	public Result<Boolean> update(String namespace, EntityDO entity){
		try {
			getSqlMapClient().update(namespace+".update",entity);
			return Result.newInstance(true, "更新数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", entity, e);
			return Result.newInstance(false, "更新数据失败", false);
		}
	}
	
	public Result<Boolean> updateBySID(String namespace,String sqlId,Entity entity){
		try {
			getSqlMapClient().update(namespace+"."+sqlId,entity);
			return Result.newInstance(true, "更新数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", entity, e);
			return Result.newInstance(false, "更新数据失败", false);
		}
	}
	
	public Result<Boolean> delete(Long id){
		return delete(this.namespace,id);
	}
	
	public Result<Boolean> delete(String namespace, Long id){
		try {
			getSqlMapClient().delete(namespace+".deleteById", id);
			return Result.newInstance(true, "删除数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(false, "删除数据失败", false);
		}
	}
	
	public Result<Boolean> deleteBySID(String sqlId, Long id){
		try {
			getSqlMapClient().delete(namespace+"."+sqlId, id);
			return Result.newInstance(true, "删除数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(false, "删除数据失败", false);
		}
	}
	
	public Result<Boolean> deleteBySID(String namespace, String sqlId, Long id){
		try {
			getSqlMapClient().delete(namespace+"."+sqlId, id);
			return Result.newInstance(true, "删除数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(false, "删除数据失败", false);
		}
	}
	
	public Result<Boolean> softDelete(Long id){
		return softDelete(this.namespace,id);
	}
	
	public Result<Boolean> softDelete(String namespace, Long id){
		try {
			getSqlMapClient().update(namespace+".softDeleteById", id);
			return Result.newInstance(true, "删除数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(false, "删除数据失败", false);
		}
	}
	
	public Result<Boolean> recover(Long id){
		return recover(this.namespace,id);
	}
	
	public Result<Boolean> recover(String namespace, Long id){
		try {
			getSqlMapClient().update(namespace+".recoverById", id);
			return Result.newInstance(true, "恢复数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(false, "恢复数据失败", false);
		}
	}
	
	public Result<Boolean> recycle(Long[] id){
		return recycle(this.namespace,id);
	}
	
	public Result<Boolean> recycle(String namespace,Long[] id){
		try {
			getSqlMapClient().delete(namespace+".recycle",id);
			return Result.newInstance(true, "回收数据成功", true);
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace, e);
			return Result.newInstance(false, "回收数据失败", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result<Entity> getById(Long id){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+".getById", id);
			if(obj != null) {
				return Result.newInstance((Entity)obj, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	public Result<Object> getById(String namespace,Long id){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+".getById", id);
			if(obj != null) {
				return Result.newInstance(obj, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(null, "获取数据失败", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result<List<Entity>> getByIds(Long[] id){
		List<Entity> list = Lists.newArrayList();
		try {
			Object obj = getSqlMapClient().queryForList(namespace+".getByIds", id);
			if(obj != null) {
				list = (List<Entity>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				
				return Result.newInstance(list, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(list, "获取数据失败", false);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Result<List<Object>> getByIds(String namespace,Long[] id){
		List<Object> list = Lists.newArrayList();
		try {
			Object obj = getSqlMapClient().queryForList(namespace+".getByIds", id);
			if(obj != null) {
				list = (List<Object>)obj;
				return Result.newInstance(list, "获取数据成功", true);
			} else {
				
				return Result.newInstance(list, "获取数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", namespace+":"+id, e);
			return Result.newInstance(list, "获取数据失败", false);
		}
	}
	
	public Result<Integer> getCount(QueryCondition query){
		try {
			Object obj = getSqlMapClient().queryForObject(namespace+".getCount", query.getQueryMap());
			if(obj != null) {
				return Result.newInstance((Integer)obj, "获取分页数据成功", true);
			} else {
				return Result.newInstance(0, "获取分页数据成功", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Result.newInstance(0, "获取分页数据成功", false);
		}
	}
	
	public Result<List<Entity>> getPage(QueryCondition query){
		try {
			Object obj = getSqlMapClient().queryForList(this.namespace+".getPage", query.getQueryMap());
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<Entity> entity = (List<Entity>)obj;
				return Result.newInstance(entity, "获取分页数据成功", true);
			} else {
				return Result.newInstance(null, "获取分页数据失败", false);
			}
		} catch (SQLException e) {
			log.error("SQL执行失败", query, e);
			return Result.newInstance(null, "获取分页数据失败", false);
		}
	}
}
