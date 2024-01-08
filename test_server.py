import http.client

# Set the hostname and port of your Java server
host = 'localhost'
port = 8889

# Specify the path you want to request (in this case, the root path '/')
path = '/'

# Create an HTTP connection to the server
conn = http.client.HTTPConnection(host, port)

try:
    # Send an HTTP GET request
    conn.request("GET", path)

    # Get the response from the server
    response = conn.getresponse()

    # Print the response status and the content of the response
    print(f"Response Status: {response.status}")
    print("Response Content:")
    print(response.read().decode())

except http.client.RemoteDisconnected as e:
    print(f"Error: {e}")

finally:
    # Close the connection
    conn.close()