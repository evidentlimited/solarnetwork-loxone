SELECT uuid_hi, uuid_lo, config_id, fsecs
FROM  solarnode.loxone_datumset
WHERE config_id = ?
ORDER BY uuid_hi ASC, uuid_lo ASC
