import day5

def get_password(prefix):
	password = ["" for x in range(8)]
	index = 0

	while "" in password:
		digest = day5.hash( prefix, index )
		if digest.startswith("00000") and digest[5] >= "0" and digest[5] <= "7":
			position = int(digest[5])
			value = digest[6]

			if password[position] == "":
				password[position] = value

		index += 1

	return "".join(password)

print( "Password: {0}".format( get_password("wtnhxymk") ) )