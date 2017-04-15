(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name app.service:customToastService
	 * @description
	 * # customToastService
	 * Service of the app
	 */

  	angular
		.module('customToast')
		.factory('CustomToastService', CustomToastService);

		CustomToastService.$inject = ['$mdToast'];

		function CustomToastService ($mdToast) {
			return {
				show: function (text, position, delay) {
					$mdToast.show(
						$mdToast.simple()
							.textContent(text)
							.position(position)
							.hideDelay(delay)
					);
				}
			}
		}
})();
