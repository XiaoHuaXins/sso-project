local redisRankString = KEYS[1]
local photoName = KEYS[2]
local user = KEYS[3]
redis.call("SINCRBY", redisRankString,photoName, 10)
local size = redis.call("SCARD",photoName)
local exist = redis.call("SISMEMBER", user)
return size,exist;
