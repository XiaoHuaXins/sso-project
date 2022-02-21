local userId = KEYS[1];
local photoId = KEY1[2];
if redis.call("SISMEMBER", photoId, userId) == 1
then
    redis.call("SREM", photoId, userId)
else
    redis.call("SADD", photoId, userId)
end