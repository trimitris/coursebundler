## Project name: coursebundler
coursebundler is an app that generates all the available course options (or course bundles) given a set of courses and rules.

## Function
App receives a list of modules with a few attributes like: 
- term
- engineering area etc.

It also gets a bunch of rules it has to follow. For example:
- must select 4 modules in michaelmas and 4 in lent
- must have at least 1 management module
- must have 2 cw etc

It will output all the possible module choices that abide by those rules.

Extra features:
- for each module selection, show possible alternatives, e.g. selections with only 1 different module. Can use similar strategy to git, that shows in red what is removed and in green what is to be added
- Descriptions of modules 

## Instructions of use (Tested with `Ubuntu 
- If java is not installed on your system, follow these instructions to install it: https://itsfoss.com/install-java-ubuntu/
- Download the project repository on your local machine (can use `git clone`)
- `cd coursebundler/`
- Run the server with: `java -jar build/libs/gs-handling-form-submission-0.1.0.jar`
- Finally, open your browser and go to: localhost:8080/select-courses
- Select a few courses from the appearing list of course codes
- Click "submit"
- Click "Get Bundles" on the appearing page. If nothing happens, this means the courses selected on the previous page do not produce a valid course combination, so go back to localhost:8080/select-courses, and select more courses
