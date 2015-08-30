# Braintree-Dropin-UI-Cordova-Plugin
Plugin for cordova projects to use Braintree Dropin/Paypal UI
This Plugin works for android. Ios one is under development.
##Installation
cordova plugin add https://github.com/mikyaj/Braintree-Dropin-UI-Cordova-Plugin.git
##How to use?
```sh
window.plugins.btreeplugin.init(function (sucessData) {
         //succes part goes here
        }, function (errorData) {
        }, [
          {
            "token": "YOUR-TOKEN-GOES-HERE"
          }
        ]);
```
