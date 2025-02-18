PULL REQUEST STANDARD EX:

# Pull Request Template

## Notes & Questions For The Reviewer

- Address är inte implementerad än då jag behöver en klass för den.
- List<Rental> favouriteRentals ej implementerad än då jag ej har en Rental klass
- Is there any fields that we are okay with being null ? (have i over validated?)
- Is Id the only setter we dont need ?
- Validation for unique email and username we have to do in controller/service with methods.
- date.now() also have to be done in methods in other classes if im not mistaken?

## What has been changed?
Added endpoints to be implemented:
Create new user
Get user by ID
Get user by e-mail
Get all users

POST   /api/users          - Create user
GET    /api/users/{id}     - Get user by id
GET    /api/users          - List all users
GET    /api/users/email/{email} - Get user by e-mail

Created UserModel and UserRepository. In UserRepository I’ve added findByEmail findByActive methods. Implemented basic validation.

## Why did I make these changes?
We had to implement CRUD operations to our API for the user model with validation for input fields.

## How can others test my code?
check out branch
start server
test every endpoint in postman
Test negativ scenarios, for example making error or trying to find resources that dont exist.

Checklist before sending in you PR
Check each box by adding an X to [ ] - [x]
[ ] - I have tested my code and it works the way i want it to
[ ] - My code follows our code standards (tex validation, names, etc)
[ ] - I have commented komplicated parts of code (if needed)
[ ] - I have uppdated dokumentation (if needed)

Remember that:
Be humble and open for feedback
Answer the comments quickly
Its okay to make mistakes
