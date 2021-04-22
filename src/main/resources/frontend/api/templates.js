import {sendRequest} from "./http.js";

const getTemplateNames = () => sendRequest({
  method: 'GET',
  url: '/templates'
}).then(
    response => {
      if (response.status !== 200) {
        throw new Error(`fetching template names failed with status ${response.status}`)
      } else {
        return response.data;
      }
    }
)

const getTemplate = (name) => sendRequest({
  method: 'GET',
  url: `/templates/${name}`
}).then(
    response => {
      if (response.status !== 200) {
        throw new Error(`fetching template failed with status ${response.status}`)
      } else {
        return response.data;
      }
    }
)

export {getTemplateNames, getTemplate}