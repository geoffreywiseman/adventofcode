import hashlib

def hash(prefix, number):
	hash = hashlib.md5()
	hash.update(prefix)
	hash.update(str(number))
	return hash.hexdigest()

def get_password(prefix):
	password = []
	index = 0

	while len(password)<8:
		digest = hash( prefix, index )
		if digest.startswith("00000"):
			password.append( digest[5] )
		index += 1

	return "".join(password)

print( "Password: {0}".format( get_password("wtnhxymk") ) )