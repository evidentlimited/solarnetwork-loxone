SELECT uuid_hi, uuid_lo, config_id, name, sort
FROM  solarnode.loxone_room
WHERE config_id = ?
ORDER BY sort DESC, lower(name) ASC
