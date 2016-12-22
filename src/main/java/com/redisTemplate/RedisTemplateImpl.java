package com.redisTemplate;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;
import com.serializer.JsonRedisSerializer;
import com.serializer.ReidsSerializationTools;
import com.serializer.StringRedisSerializer;

public class RedisTemplateImpl implements RedisTemplate {


	private JedisCluster jedisCluster;

	@SuppressWarnings("rawtypes")
	private JsonRedisSerializer jsonRedisSerializer;
	
	private StringRedisSerializer stringRedisSerializer;

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@SuppressWarnings("rawtypes")
	public void setJsonRedisSerializer(JsonRedisSerializer jsonRedisSerializer) {
		this.jsonRedisSerializer = jsonRedisSerializer;
	}
	
	public void setStringRedisSerializer(StringRedisSerializer stringRedisSerializer) {
		this.stringRedisSerializer = stringRedisSerializer;
	}

	public Boolean hasKey(Object key){
		return jedisCluster.exists(objectStringToByte(key));
	}

	public void delete(Object key){
		jedisCluster.del(objectStringToByte(key));
	}

	public void set(Object key, Object value) {
		byte[] byValue = jsonRedisSerializer.serialize(value);
		jedisCluster.set(objectStringToByte(key), byValue);
	}

	public Object get(Object key){
		byte[] byValue = jedisCluster.get(objectStringToByte(key));
		return jsonRedisSerializer.deserialize(byValue);
	}

	public void set(Object key, Object value, Long timeout, TimeUnit timeUnit) {
		if(!TimeUnit.MILLISECONDS.equals(timeUnit)){
			Long seconds = timeUnit.toSeconds(timeout);
			jedisCluster.setex(objectStringToByte(key), seconds.intValue(), objectObjectToByte(value));
		}else{
			jedisCluster.psetex((String)key, timeout, (String)value);
		}
	}

	public Long leftPush(Object key, Object value) {
		return jedisCluster.lpush(objectStringToByte(key), objectObjectToByte(value));
	}

	public Long leftPushAll(Object key, Object... values) {
		byte[][] by=new byte[values.length][];
		for(int i=0;i<values.length;i++){
			by[i]=objectObjectToByte(values[i]);
		}
		return jedisCluster.lpush(objectStringToByte(key), by);
	}

	public Object leftPop(Object key) {
		return jsonRedisSerializer.deserialize(jedisCluster.lpop(objectStringToByte(key)));
	}

	public Long rightPush(Object key, Object value) {
		return jedisCluster.rpush(objectStringToByte(key), objectObjectToByte(value));
	}

	public Long rightPushAll(Object key, Object... values) {
		byte[][] by=new byte[values.length][];
		for(int i=0;i<values.length;i++){
			by[i]=objectObjectToByte(values[i]);
		}
		return jedisCluster.rpush(objectStringToByte(key), by);
	}

	public Object rightPop(Object key) {
		return jsonRedisSerializer.deserialize(jedisCluster.rpop(objectStringToByte(key)));
	}

	public Long listSize(Object key) {
		return jedisCluster.llen(objectStringToByte(key));
	}

	@SuppressWarnings("unchecked")
	public List<Object> range(Object key, long start, long end) {
		List<byte[]> byList = jedisCluster.lrange(objectStringToByte(key), start, end);
		return ReidsSerializationTools.deserialize(byList, jsonRedisSerializer);
	}

	public Long addSet(Object key, Object... values) {
		byte[][] by=new byte[values.length][];
		for(int i=0;i<values.length;i++){
			by[i]=objectObjectToByte(values[i]);
		}
		return jedisCluster.sadd(objectStringToByte(key), by);
	}

	@SuppressWarnings("unchecked")
	public Set<Object> getSet(Object key) {
		Set<byte[]> bySet = jedisCluster.smembers(objectStringToByte(key));
		return ReidsSerializationTools.deserialize(bySet, jsonRedisSerializer);
	}

	public Object popSet(Object key) {
		return jsonRedisSerializer.deserialize(jedisCluster.spop(objectStringToByte(key)));
	}
	
	public Long remvoeKeyFromSet(Object key, Object... set) {
		byte[][] by=new byte[set.length][];
		for(int i=0;i<set.length;i++){
			by[i]=objectObjectToByte(set[i]);
		}
		return jedisCluster.srem(objectStringToByte(key), by);
	}

	public Long getSetSize(Object key) {
		return jedisCluster.scard(objectStringToByte(key));
	}

	public void addHash(Object key, Object field, Object value) {
		jedisCluster.hset(objectStringToByte(key), objectStringToByte(field), objectObjectToByte(value));
	}

	@SuppressWarnings("unchecked")
	public List<Object> getHashValues(Object key) {
		Collection<byte[]> hvals = jedisCluster.hvals(objectStringToByte(key));
		return (List<Object>)ReidsSerializationTools.deserialize(hvals, jsonRedisSerializer);
	}

	public Object getHash(Object key, Object field) {
		return jsonRedisSerializer.deserialize(jedisCluster.hget(objectStringToByte(key), objectStringToByte(field)));
	}

	public Set<String> getHashKeys(Object key) {
		Set<byte[]> hkeys = jedisCluster.hkeys(objectStringToByte(key));
		return ReidsSerializationTools.deserialize(hkeys, stringRedisSerializer);
	}

	public Long incr(Object key) {
		return jedisCluster.incr(objectStringToByte(key));
	}

	public Long getExpire(Object key, TimeUnit timeUnit) {
		try {
			return timeUnit.convert(jedisCluster.pttl((String)key), TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			return timeUnit.convert(jedisCluster.ttl(objectStringToByte(key)), TimeUnit.SECONDS);
		}
	}

	public boolean isSetMember(Object key, Object member) {
		return jedisCluster.sismember(objectStringToByte(key), objectObjectToByte(member));
	}

	public Long incrBy(Object key, long delta) {
		return jedisCluster.incrBy(objectStringToByte(key), delta);
	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> hashGetAll(Object key) {
		Map<byte[], byte[]> byMap = jedisCluster.hgetAll(objectStringToByte(key));
		return ReidsSerializationTools.deserialize(byMap, stringRedisSerializer, jsonRedisSerializer);
	}

	public void addHashAll(Object key, Map<Object, Object> map) {
		final Map<byte[], byte[]> hashes = new LinkedHashMap<byte[], byte[]>(map.size());
		for (Entry<Object, Object> entry : map.entrySet()) {
			hashes.put(objectStringToByte(entry.getKey()), objectObjectToByte(entry.getValue()));
		}
		jedisCluster.hmset(objectStringToByte(key), hashes);
	}

	public void deleteHash(Object key, Object... hashkeys) {
		byte[][] by=new byte[hashkeys.length][];
		for(int i=0;i<hashkeys.length;i++){
			by[i]= objectStringToByte(hashkeys[i]);
		}
		jedisCluster.hdel(objectStringToByte(key), by);
	}

	public Long getZSetCount(Object key) {
		return jedisCluster.zcard(objectStringToByte(key));
	}

	public void addOneToZSet(Object key, Object value, double score) {
		jedisCluster.zadd(objectStringToByte(key), score, objectObjectToByte(value));
	}

	public void addSetToZSet(Object key, Set<TypedTuple<Object>> setObject) {
		for(TypedTuple<Object> obj:setObject){
			jedisCluster.zadd(objectStringToByte(key), obj.getScore(), objectObjectToByte(obj.getValue()));
		}
	}

	public void removeOneFromZSet(Object key, Object value) {
		jedisCluster.zrem(objectStringToByte(key), objectObjectToByte(value));
	}

	public Set<TypedTuple<Object>> getZRevrangeWithScores(Object key, long start, long end) {
//		Set<byte[]> setZre = jedisCluster.zrevrange(objectStringToByte(key), start, end);
//		Set<TypedTuple<Object>> setType=new HashSet<TypedTuple<Object>>();
//		for(byte[] by:setZre)
//		{
//			Object Obby = jsonRedisSerializer.deserialize(by);
//			TypedTuple<Object> tup=new DefaultTypedTuple<Object>(Obby, null);
//			setType.add(tup);
//		}
//		return setType;
		Set<Tuple> set = jedisCluster.zrevrangeWithScores(objectStringToByte(key), start, end);
		return deserializeTupleValues(set);
	}
	
	Set<TypedTuple<Object>> deserializeTupleValues(Collection<Tuple> rawValues) {
		if (rawValues == null) {
			return null;
		}
		Set<TypedTuple<Object>> set = new LinkedHashSet<TypedTuple<Object>>(rawValues.size());
		for (Tuple rawValue : rawValues) {
			set.add(deserializeTuple(rawValue));
		}
		return set;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	TypedTuple<Object> deserializeTuple(Tuple tuple) {
		Object value = jsonRedisSerializer.deserialize(tuple.getBinaryElement());
		return new DefaultTypedTuple(value, tuple.getScore());
	}

	@SuppressWarnings("unchecked")
	public List<Object> getHashMultiGet(Object key, Collection<Object> hashKeys) {
		final byte[][] rawHashKeys = new byte[hashKeys.size()][];
		int counter = 0;
		for (Object hashKey : hashKeys) {
			rawHashKeys[counter++] =objectStringToByte(hashKey);
		}
		List<byte[]> hmget = jedisCluster.hmget(objectStringToByte(key), rawHashKeys);
		return ReidsSerializationTools.deserialize(hmget, jsonRedisSerializer);
		 
		
	}

	public List<Object> getSortedObject(Integer pageIndex, Integer pageSize,
			String hashKey, String zsetKey) {
		if(hasKey(hashKey) && hasKey(zsetKey))
		{
			long start = (pageIndex-1)*pageSize;
			long end = pageIndex*pageSize-1;
			Set<TypedTuple<Object>> idZSet = getZRevrangeWithScores
					(zsetKey, start, end);
			List<Object> hashKeys=new LinkedList<Object>();
			for(TypedTuple<Object> idTuple : idZSet){
				hashKeys.add(idTuple.getValue());
			}
			List<Object> list = getHashMultiGet(hashKey, hashKeys);
			return list;
		}
		return null;
	}
	
	/**
	 * 将Object转换成String然后转换成byte类型
	 * @param key
	 * @return
	 */
	public byte[] objectStringToByte(Object key){
		return stringRedisSerializer.serialize((String)key);
	}
	
	/**
	 * 将Object转换成（json）Byte类型
	 * @param value
	 * @return
	 */
	
	public byte[] objectObjectToByte(Object value){
		return jsonRedisSerializer.serialize(value);
	}
	
}
