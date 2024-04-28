# BookRepo
Parses and checks book list items
This code is for checking a list of strings like "authorName|bookname|category" then it parses it into this structure:
```[
{
 "kategori" : <kategorinavn>
 "antall": <antall bøker i kategori>
 "bøker": [
 {
 "navn": <boknavn>
 "forfattere": [<liste av forfatternavn>]
 },
 ...
 ]
},
...
]```
The code will also check for two conditions:
1. If the same book is found in more than category then we throw an error.
2. If the author is repeated for the same book more than once then also an error will be thrown.
3. An Error json object has been created which will be populated with the errorMessae, errorCode and errorId fields.
