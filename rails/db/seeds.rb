# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

User.create(email: "dr@mail.com", password: "test1234")
User.create(email: "ab@mail.com", password: "test1234")

Connection.create(ip: "127.0.0.1", port: 8090, used: false)
Connection.create(ip: "127.0.0.1", port: 8091, used: false)
Connection.create(ip: "127.0.0.1", port: 8092, used: false)
Connection.create(ip: "127.0.0.1", port: 8093, used: false)
Connection.create(ip: "127.0.0.1", port: 8094, used: false)