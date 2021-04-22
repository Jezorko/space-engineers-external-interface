const baseUrl = `http://${window.location.host}`;

const response = (data, status) => ({
  data: data,
  status: status
})

const sendRequest = (request) => {
  return new Promise((resolve, reject) => {
    try {
      const xmlHttp = new XMLHttpRequest();
      console.log(`sending request to ${request.url}`, request)
      xmlHttp.open(request.method, baseUrl + request.url, false);
      xmlHttp.send(request.body);
      const responseText = xmlHttp.responseText;
      if (responseText === undefined || responseText === null || responseText === '') {
        resolve(response(responseText, xmlHttp.status));
      } else {
        try {
          resolve(response(JSON.parse(responseText), xmlHttp.status));
        } catch (jsonParseException) {
          console.error("failed to parse response", responseText)
          reject(jsonParseException);
        }
      }
    } catch (exception) {
      reject(exception);
    }
  })
};

export {baseUrl, sendRequest}