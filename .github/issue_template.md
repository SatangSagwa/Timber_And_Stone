ISSUE STANDARD EX:

# Implement postmapping for user model
## Description
Implement postmapping for the user model and test endpoint in postman with simple validation.

## Blocked by:
Issue #3

## Functionality
### Endpoints to be implemented:
- [ ] Postmapping for new user
- [ ] Set createdAt and updatedAt automatically (date.now)
- [ ] Validation for null/empty fields and eventually min/max values
- [ ] Validation for unique username and email

## Technical requirements
- [ ] All endpoints has to return valid HTTP-statuscodes. (201 created, 400 bad request)
- [ ] Basic validation has to be implemented

## API-structure
POST   /api/users          - Create user

## Acceptance kriteria
All listed endpoints are implemented and tested
The API returns JSON response
Basic validation has been implemented
The code is following our code standard
The API has been manually tested

## Technical details
Framework: Spring Boot
Database: MongoDB
Difficulty: Easy
Time estimate: 1-2 days
