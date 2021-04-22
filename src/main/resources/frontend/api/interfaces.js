import {sendRequest} from "./http.js";

const getInterfaceNames = () => sendRequest({
  method: 'GET',
  url: '/interfaces'
}).then(
    response => {
      if (response.status !== 200) {
        throw new Error(`fetching interface names failed with status ${response.status}`)
      } else {
        return response.data;
      }
    }
)

const getInterface = (name) => sendRequest({
  method: 'GET',
  url: `/interfaces/${name}`
}).then(
    response => {
      if (response.status !== 200) {
        throw new Error(`fetching interface failed with status ${response.status}`)
      } else {
        return response.data;
      }
    }
)

export {getInterfaceNames,getInterface}