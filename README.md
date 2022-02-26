# Self-Care-App
Java application, which is motivated by the importance of self-care and self-love. It allows creating accounts, getting motivational quotes and adding, filtering, and sorting personal journals. There are a set of commands that can be executed in the console after starting the application. Important functionalities that I learned from the MJT course are to be seen in the implementation as well as good architecture, clean code conventions and unit tests.

### Commands
- **register** - _if `<username>` is free_
``` shell
$ register <username> <password>
```
- **log in** - _if `<username>` and `<password>` are correct_
``` shell
$ login <username> <password>
```
- **log out** - _if user is logged_
``` 
$ logout
```
- **create new journal**
``` shell
$ create-journal <title> <content>
```
- **list all journals** - _all journals of current user_
``` shell
$ list-all-journals
```
- **find by title**
``` shell
$ find-by-title <title>
```
- **find by keywords** - _ordered by the occurences (desc)_
``` shell
$ find-by-keywords <keywords>
```
- **find by date** - <date> _in format YYYY-MM-DD_
``` shell
$ find-by-date <date>
```
- **sort by title**
``` shell
$ sort-by-title <[asc/desc]>
```
- **sort by date**
``` shell
$ sort-by-date <[asc/desc]>
```
- **get quote** - _random motivational quote_
``` shell
$ get-quote
```
- **disconnect**
``` shell
$ disconnect
```
   
   
### Functionalities
- files I/O operations
- storing data in files
- client-server communication (multiple clients at once)
- REST API calls
- Stream API
- input validation



