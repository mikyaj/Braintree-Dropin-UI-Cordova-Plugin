function BrainTreePayment() {
    console.log('Construct');

}

BrainTreePayment.prototype.init = function (successCallback, failureCallback, args)
{
    console.log('BrainTreePayment.getCardInfoz start');
    cordova.exec(successCallback, failureCallback, "BraintreePlugin", "init", args);
    console.log('BrainTreePayment.getCardInfoz done');
};

BrainTreePayment.prototype.getCardInfo = function (successCallback, failureCallback)
{
    console.log('BrainTreePayment.getCardInfoz start');
    cordova.exec(successCallback, failureCallback, "BraintreePlugin", "getCreditCardInfo", []);
    console.log('BrainTreePayment.getCardInfoz done');
};

BrainTreePayment.prototype.dismiss = function (successCallback, failureCallback)
{
    cordova.exec(successCallback, failureCallback, "BraintreePlugin", "dismiss", []);
    console.log('Dismiss!!');
};

if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.btreeplugin) {
    window.plugins.btreeplugin = new BrainTreePayment();
}
if (typeof module != 'undefined' && module.exports) {
    module.exports = BrainTreePayment;
}
