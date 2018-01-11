# Http Timeout Handling Poc

Scenario: Service calls webhook and gives it a certain amount of time before it abandons the request (timeout).

Quick poc to better understand how intentionally timing out http requests affects the connection between client and server and how the server can notice that a timeout happened.