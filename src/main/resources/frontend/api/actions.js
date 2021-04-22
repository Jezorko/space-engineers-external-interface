import {sendRequest} from "./http.js";

const triggerAction = (action) => sendRequest({
  method: 'POST',
  url: `/actions/${action}`,
  body: '{}'
}).then(
    response => {
      if (response.status !== 204) {
        throw new Error(`triggering action failed with status ${response.status}`)
      } else {
        return response.data;
      }
    }
)

export {triggerAction}