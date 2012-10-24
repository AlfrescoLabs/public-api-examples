/* Use the values from your account on the Alfresco Developer Portal (developer.alfresco.com)
 * 
 * Set client_id to the value of "Api Key" (on the "Auth" tab when you edit an application in the portal)
 * Set client_secret to the value of "Key Secret" (on the "Auth" tab when you edit an application in the portal)
 * For the simple html example to work, the redirect_uri should be set to "http://localhost:8090/alfapi/example_htmlonly/mycallback.html"
 * (The redirect_uri MUST match the value you set as the "Callback URL" on the "Auth" tab in the portal)
 */
var config = {
  client_id:"l7xxc2c72c2ad8144f54a1164a20850b2b84_replace_me",
  client_secret:"48794a52e7e84eddb45e78aecb7552ce_replace_me",
  redirect_uri:"http://localhost:8090/alfapi/example_htmlonly/mycallback.html",
  authURL:"https://api.alfresco.com/auth/oauth/versions/2/authorize",
  tokenURL:"https://api.alfresco.com/auth/oauth/versions/2/token",
  scope:"public_api",
  aTestUrl:"https://api.alfresco.com/alfresco.com/public/alfresco/versions/1/sites/public-api-trial-site"
};
