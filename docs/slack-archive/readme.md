# Slack Archive
This service store all messages from the Juja community slack channels

## Technical characteristics of the project:

* JDK8
* Mongo
* SpringBoot
* SpringData
* SpringMVC
* Swagger
* jUnit
* Lombok

### ARC-F1
***ARC-F1 As a user I want to get list of available slack channels***

* ARC-F1-D1 Result of the command is the List of channel ids and names. 

* ARC-F1-URL
```
    url - "/channels"
    method - GET
```

* ARC-F1-RSP - Channels entity
```
    [
        {
         "id":"string",
         "name":"string"
        }
    ] 
```
* ARC-F1-RSP-ERR
```
    {
    error message
    }
```


### ARC-F2
***ARC-F2 As a user I want to get N last messages in the selected channel***

* ARC-F2-D1 Input parameters: channel id and number of last messages
* ARC-F2-D2 Message it's an entity, that contains message id, time, type, user and text.
* ARC-F2-D3 Result of the command is list of message entities.

* ARC-F2-URL
```
    url - "/messages"
    method - GET
```
* ARC-F2-REQ
```
   {
      "id":"string",
      "number":int
   }
```
* ARC-F2-RSP
```
    [
        {
            "id":"string",
            "time":long,
            "type":"string",
            "user":"string",
            "text:"string"         
        } 
    ]
```
* ARC-F2-RSP-ERR
```
    {
    error message
    }
```


### ARC-F3
***ARC-F3 As a system I want to store all raw json slack messages into database***
* ARC-F3-D1 System stores all messages into database


### ARC-F4
***ARC-F4 As a system I want to store selected by type slack messages into database with parsed format***
* ARC-F4-D1 System stores selected by type messages
* ARC-F4-D2 Before storing the message must be parsed to selected fields
