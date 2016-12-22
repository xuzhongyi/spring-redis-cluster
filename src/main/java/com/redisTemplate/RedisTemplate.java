package com.redisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Tuple;

public interface RedisTemplate {
	/**
	 * hasKey:(检查给定 key是否存在,相当于redis EXISTS 命令). <br/>
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public Boolean hasKey(Object key);

	 /**
     * 
     * delete:(删除已存在的键,不存在的 key会被忽略,相当于Redis DEL命令). <br/>
     *
     * @author alex shan
     * @param key
	 * @throws Exception 
     */
	
	public void delete(Object key);

	/**
	 * set:(设置给定 key 的值,如果 key已经存储其他值,SET 就覆写旧值,且无视类型,相当于Redis SET 命令). <br/>
	 * 需要序列化成为String
	 * @param key
	 * @param value
	 */
	public void set(Object key, Object value);

	/**
	 * 获取一个Object对象
	 * @param key
	 */
	public Object get(Object key);

	/**
     * 
     * set:(为指定的 key设置值及其过期时间,如果 key已经存在,将会替换旧的值,单位时间为秒.相当于Redis SETEX命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param value
     * @param timeout
     */
	public void set(Object key, Object value, Long timeout, TimeUnit timeUnit);
	
	 /**
     * 
     * leftPush:(将一个值插入到列表头部, 如果 key不存在,一个空列表会被创建并执行 LPUSH 操作,相当于Redis Lpush 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param value
     * @return
     */
    public Long leftPush(Object key, Object value);
    
    /**
     * 
     * leftPushAll:(将一个或多个值插入到已存在的列表头部,相当于Redis Lpushx命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param values
     * @return
     */
    public Long leftPushAll(Object key, Object... values);
	
    /**
     * 
     * leftPop:(移除并返回列表的第一个元素,相当于Redis Lpop 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Object leftPop(Object key);
    /**
     * 
     * rightPush:(将一个值插入到列表的尾部,相当于Redis Rpush 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param value
     * @return
     */
    public Long rightPush(Object key, Object value);
    
    /**
     * 
     * rightPushAll:(将一个或多个值插入到已存在的列表尾部,相当于Redis Rpushx命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(Object key, Object... values);
    
    /**
     * 
     * rightPop:(移除并返回列表的最后一个元素,相当于Redis Rpop 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Object rightPop(Object key);
    
    /**
     * 
     * 
     * listSize:(返回列表的长度,相当于Redis Llen命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Long listSize(Object key);
    
    /**
     * 
     * range:(返回列表中指定区间内的元素,区间以偏移量 START 和 END 指定. 其中 0 表示列表的第一个元素,
     * 1 表示列表的第二个元素,以此类推. 你也可以使用负数下标,以 -1 表示列表的最后一个元素, -2 表示列表的倒数第二个元素,以此类推
     * 相当于Redis Lrange命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> range(Object key,long start,long end); 
    
    /**
     * 
     * addSet:(将一个或多个成员元素加入到集合中,相当于Redis Sadd命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param values
     * @return
     */
    public Long addSet(Object key,Object ... values);
    
    /**
     * 
     * getSet:(返回集合中的所有的成员,相当于Redis Smembers 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Set<Object> getSet(Object key);
    
    /**
     * 
     * popSet:(移除并返回集合中的一个随机元素,相当于Redis Spop命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Object popSet(Object key);
    
     /**
     * 
     * remvoeKeyFromSet:(移除集合中的一个或多个成员元素,相当于Redis Srem 命令). <br/>
     *
     * @author alex shan
     * @param set
     * @param key
     * @return
     */
    public Long remvoeKeyFromSet(Object key, Object... set);
    
     /**
     * 
     * getSetSize:(返回集合中元素的数量,相当于Redis Scard 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Long getSetSize(Object key);
    
    /**
     * 
     * addHash:(为哈希表中的字段赋值,相当于Redis Hset 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param field
     * @param value
     */
    public void addHash(Object key,Object field,Object value);
    
    /**
     * 
     * getHashValues:(返回哈希表所有字段的值,相当于Redis Hvals 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public List<Object> getHashValues(Object key);
    
    /**
     * 
     * getHash:(返回哈希表中指定字段的值,相当于Redis Hget 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @param field
     * @return
     */
    public Object getHash(Object key,Object field);
    
    /**
     * 
     * getHashKeys:(获取哈希表中的所有字段名,相当于Redis Hkeys 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Set <String> getHashKeys(Object key);
    
    /**
     * 
     * incr:(将 key中储存的数字加上1,相当于Redis Incr 命令). <br/>
     *
     * @author alex shan
     * @param key
     * @return
     */
    public Long incr(Object key);
	
	/**
	 * 
	 * getExpire:(获取key的过期时间). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param unit
	 * @return
	 */
	public Long getExpire(Object key,TimeUnit unit);
	
	/**
	 * 
	 * isSetMember:(判断成员元素是否是集合的成员,相当于Redis Sismember 命令). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean isSetMember(Object key, Object member);
	
	/**
	 * 
	 * incrBy:(将 key中储存的数字加上指定的增量值,相当于Redis Incrby 命令). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param delta
	 * @return
	 */
	public Long incrBy(Object key,long delta);
	
	/**
	 * 	
	 * hashGetAll:(返回哈希表中所有的字段和值,相当于Redis Hgetall 命令). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @return
	 */
	public Map<Object,Object> hashGetAll(Object key);
	
	/**
	 * 
	 * addHashAll:(同时将多个 field-value (字段-值)对设置到哈希表中,相当于Redis Hmset 命令). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param map
	 */
	public void addHashAll(Object key,Map<Object,Object> map);
	
	/**
	 * 
	 * deleteHash:(删除哈希表 key 中的一个或多个指定字段,相当于Redis Hdel 命令). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param hashkeys
	 */
	public void deleteHash(Object key,Object ... hashkeys);
	
	/**
	 * 
	 * getZSetCount:(Redis Zcard 命令用于计算集合中元素的数量). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @return
	 */
	public Long getZSetCount(Object key);
	
	/**
	 * 
	 * addOneToZSet:(Redis Zadd 命令用于将一个成员元素及其分数值加入到有序集当中). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param value
	 * @param score
	 */
	public void addOneToZSet(Object key,Object value,double score);
	
	/**
	 * 
	 * addSetToZSet:(Redis Zadd 命令用于将多个成员元素及其分数值加入到有序集当中). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param setObject
	 */
	public void addSetToZSet(Object key,Set<TypedTuple<Object>> setObject);
	
	/**
	 * 
	 * removeOneFromZSet:(Redis Zrem 命令用于移除有序集中的一个成员，不存在的成员将被忽略). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param value
	 */
	public void removeOneFromZSet(Object key, Object value);
	
	/**
	 * 
	 * getZRevrangeWithScores:(Redis Zrevrange 命令返回有序集中，指定区间内的成员). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<TypedTuple<Object>> getZRevrangeWithScores(Object key,long start,long end);
	
	/**
	 * 
	 * getHashMultiGet:(Redis Hmget 命令用于返回哈希表中，一个或多个给定字段的值). <br/>
	 *
	 * @author alex shan
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public List<Object> getHashMultiGet(Object key,Collection<Object> hashKeys);
	
   /**
     * 
     * getSortedProduct:(获取业务对象集合). <br/>
     *
     * @author alex shan
     * @param pageIndex
     * @param pageSize
     * @param hashKey
     * @param zsetKey
     * @return
     */
	public List<Object> getSortedObject(Integer pageIndex,Integer pageSize,String hashKey,String zsetKey);
    
	}
