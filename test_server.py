import http.client

class MyHTTPClient:
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.conn = http.client.HTTPConnection(self.host, self.port)

    def make_request(self, method, path):
        try:
            # Send an HTTP request
            self.conn.request(method, path)

            # Get the response from the server
            response = self.conn.getresponse()

            # Print the response status and the content of the response
            # print(f"Response Status: {response.status}")
            # print("Response Content:")
            # print(response.read().decode())

            return [response.status, response.read().decode()]

        except http.client.RemoteDisconnected as e:
            print(f"Error: {e}")

    def close_connection(self):
        # Close the connection
        self.conn.close()

# Example usage
if __name__ == "__main__":
    # Set the hostname and port of your Java server
    host = 'localhost'
    port = 8889

    # Specify the path you want to request (in this case, the root path '/')
    path = '/access'

    # Create an instance of MyHTTPClient
    my_client = MyHTTPClient(host, port)

    try:
        # test with user 1 and access 1
        path += '/1/1'
        print(f"Requesting {path}...")

        # Make an HTTP GET request using the method in the class
        [status, content] = my_client.make_request('GET', path)
        print(f"Response Status: {status}")
        print("Response Content:")
        print(content)


        # test with user 1 and access 2
        path = '/access/1/2'
        print(f"Requesting {path}...")

        # Make an HTTP GET request using the method in the class
        [status, content] = my_client.make_request('GET', path)
        print(f"Response Status: {status}")
        print("Response Content:")
        print(content)


        # test with user 2 and access 1
        path = '/access/2/1'
        print(f"Requesting {path}...")

        # Make an HTTP GET request using the method in the class
        [status, content] = my_client.make_request('GET', path)
        print(f"Response Status: {status}")
        print("Response Content:")
        print(content)

    finally:
        # Close the connection using the method in the class
        my_client.close_connection()
