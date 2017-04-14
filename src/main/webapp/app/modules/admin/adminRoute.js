'use strict';

	/**
	* @ngdoc function
	* @name app.route:AdminRoute
	* @description
	* # AdminRout
	* Route for the admin module
	*/

angular.module('admin')
	.config(['$stateProvider', function ($stateProvider) {
		$stateProvider
			.state('admin', {
				templateUrl: 'app/modules/admin/admin.html',
				controller: 'AdminCtrl',
				controllerAs: '$adminCtrl'
			});
	}]);
