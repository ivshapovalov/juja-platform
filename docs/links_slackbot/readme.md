# Links Slackbot
Links slack bot performs processing of commands from slack chat. 
If commands and token are valid Slack bot generates requests to 
Links microservice [Links](url).
It generates response to slack in case of successful command execution or error.


## Description of slack slash command
For the [Links](https://github.com/JujaLabs/docs/tree/master/architecture/links) purposes we use slack chat ability. 
It calls "slash command". This commands starts with "/" symbol ( "/away" for example). 
Command must be one not empty word. It can have parameters ("/command param1 param2" for example). 
Slack lets developers to create commands, which refer to any endpoint of microservices. Slash
commands support only POST method.

## Slack chat request example
HTTP POST
Content-type = application/x-www-form-urlencoded
```
token=48aQhlnlyHNlMx5f23DGgzqH
team_id=T0001
team_domain=example
channel_id=C2147483705
channel_name=test
user_id=U2147483697
user_name=Steve
command=/weather
text=params
response_url=https://hooks.slack.com/commands/1234/5678
```

## Request validation
All requests from slack are validated by unique token.

## Rules of response to slack chat.
User must receive the response from slackbot in case of successful command execution or error.
Slack chat has 3000 ms time out for response. That's why Slackbot sends two responses to Slack: Instant and Delayed.
All responses must contain HTTP 200 "OK" status code. All additional information must be in text line.

Read more  about [slash commands](https://api.slack.com/slash-commands)

## Technical characteristics of the project:
* JDK 8
* SpringBOOT
* SpringMVC
* jUnit
* JBot Framework

## Abilities:

***SLB-F1 As a user I want to save useful link***

Slackbot tasks:
* SLB-F1-D1 Validate token;
* SLB-F1-D2 Send request to Links Service to save new Link and receive response (Link Entity);
* SLB-F1-D3 Send response to slack user in case of successful command execution or error;

* SLB-F1-CMD 
```
    /links-add url  
```
* SLB-F1-URL
```
    url - commands/links/add
    method - POST
```

* SLB-F1-REQ
   ```
    Content-type = application/x-www-form-urlencoded
    
    token=...
    team_id=...
    team_domain=...
    channel_id=...
    channel_name=...
    user_id=...
    user_name=...
    command=...
    text=...
    response_url=...
    ```

* SLB-F1-RSP-INS-OK
   ```
    {
    text: Thanks, Saving link job started!
    }
   ```
* SLB-F1-RSP-DEL-OK
   ```
    {
    text: Thanks, your link 'example-url' saved!
    }
   ```
* SLB-F1-RSP-ERR
   ```
    {
    text:{error message}.
    }
   ```
    
