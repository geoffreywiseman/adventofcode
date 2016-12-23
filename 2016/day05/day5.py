import hashlib

def hash(prefix, number):
	hash = hashlib.md5()
	hash.update(prefix)
	hash.update(str(number))
	return hash.hexdigest()
