SELECT uuid_hi, uuid_lo, config_id, name, sort, ctype
FROM  solarnode.loxone_category
WHERE config_id = ?
ORDER BY sort DESC, lower(name) ASC
