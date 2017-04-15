(function () {

	angular.module("supplychain-web")
		.directive("dateValidator", dateValidator);

	function dateValidator() {
		return {
			restrict: 'A',
			require: 'ngModel',
			link: function(scope, element, attrs, ngModel){
				element.bind('blur', function (e) {
					if (!ngModel || element.val() === "__/__/____") return;
					var isDateValid = moment(element.val(), "DD-MM-YYYY").isValid();
					ngModel.$setValidity('invalid-date', isDateValid);
				});
			}
		}
	}
})();
