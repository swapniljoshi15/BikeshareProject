var bikeshareapp = angular.module('bikeshareapp', [ 'ngRoute', 'ngResource',
		'ngMap', 'uiGmapgoogle-maps', 'smart-table']);

bikeshareapp.run(function($rootScope) {
    $rootScope.OffNavTabs = true;
    $rootScope.OffLogoutTabs = true;
});

//factory for sharing data between controller
bikeshareapp.factory('dataSharing', function() {
	 var sharedData = {}
	 function set(data) {
		 sharedData = data;
	 }
	 function get() {
		 return sharedData;
	 }

	 return {
	  set: set,
	  get: get
	 }

});


// configure our routes
bikeshareapp.config(function($routeProvider) {
	$routeProvider

	// route for the login page
	.when('/', {
		templateUrl : 'template.html',
		controller : 'loginController'
	})

	// route for the signup page
	.when('/signup', {
		templateUrl : 'signup.html',
		controller : 'signupController'
	})

	// route for the authentication page
	.when('/home', {
		templateUrl : 'home.html',
		controller : 'authenticatecontroller'
	})

	// booking bike
	.when('/receipt', {
		templateUrl : 'receipt.html',
		controller : 'receiptController'
	})
	
	// booking details
	.when('/bookingdetails', {
		templateUrl : 'bookingdetails.html',
		controller : 'bookingdetailsController'
	})

	// booking details
	.when('/bookingdetailscancel', {
		templateUrl : 'bookingdetails.html',
		controller : 'bookingdetailsCancelController'
	})
	
	// logout
	.when('/logout', {
		templateUrl : 'template.html',
		controller : 'logoutController'
	})
	
	.otherwise({
		redirectTo : '/'
	});
});

bikeshareapp.directive('emailUnique', function($http, $timeout) {
	 return {
	    restrict: 'A',
	    require: 'ngModel',
	    link: function(scope, element, attrs, ngModel) {
	    	
	    	
			// set "valid" by default on typing
	        element.bind('keyup', function () {

		    	
		    	 var response = $http.get("../../api/v1/checkUniqueEmail/" + element.val());
		    	 
		          response.success(function(dataFromServer, status,
							headers, config) {
							if (dataFromServer == 'true') {
								scope.signupform_unique = false;
								return ngModel.$setValidity('unique',true);
							}else{
								scope.signupform_unique = true;
								return ngModel.$setValidity('unique', false);
							}
						});
				response.error(function(data, status, headers, config) {
					if (response.status === 401
							|| response.status === 400) {
						$scope.loginform_error = "Invalid request";
						$location.url('/');
						return ngModel.$setValidity('unique',true);
					}
				});
	        });
			
	       }
	    };
	});


bikeshareapp.directive('nameUnique', function($http, $timeout) {
	 return {
	    restrict: 'A',
	    require: 'ngModel',
	    link: function(scope, element, attrs, ngModel) {
	    	
	    	
			// set "valid" by default on typing
	        element.bind('keyup', function () {

		    	
		    	 var response = $http.get("../../api/v1/checkUniqueUsername/" + element.val());
		    	 
		          response.success(function(dataFromServer, status,
							headers, config) {
							if (dataFromServer == 'true') {
								scope.signupform_unique = false;
								return ngModel.$setValidity('unique',true);
							}else{
								scope.signupform_unique = true;
								return ngModel.$setValidity('unique', false);
							}
						});
				response.error(function(data, status, headers, config) {
					if (response.status === 401
							|| response.status === 400) {
						$scope.loginform_error = "Invalid request";
						$location.url('/');
						return ngModel.$setValidity('unique',true);
					}
				});
	        });
			
	    	/*
	       var stop_timeout;
	       return scope.$watch(function() {
	          return ngModel.$modelValue;
	       }, function(name) {
	          $timeout.cancel(stop_timeout);
	          
	          var yourFieldName = elem.attr('unique');
	          var response = $http.get("../../api/v1/checkUniqueUsername/" + name);
	 
	          response.success(function(dataFromServer, status,
						headers, config) {
						if (dataFromServer) {
							return scope.signupform.signupform_username.$setValidity('unique',true);
						}else{
							return scope.signupform.signupform_username.$setValidity('unique', false);
						}
					});
			response.error(function(data, status, headers, config) {
				if (response.status === 401
						|| response.status === 400) {
					$scope.loginform_error = "Invalid request";
					$location.url('/');
					return ngModel.$setValidity('unique',true);
				}
			});
	          
	       });*/
	       }
	    };
	});



/*
 * //interceptor bikeshareapp.factory('httpInterceptor', function
 * httpInterceptor ($q, $window, $location) { return function (promise) { var
 * success = function (response) { return response; };
 * 
 * var error = function (response) { if (response.status === 401 ||
 * response.status === 400) { $location.url('/'); }
 * 
 * return $q.reject(response); };
 * 
 * return promise.then(success, error); }; });
 */




// create the controller and inject Angular's $scope
bikeshareapp.controller('loginController', function($scope, $rootScope, $http, $location) {
	// create a message to display in our view
	
	//session validation
	var response = $http.get("../../api/v1/loggedin");
	response.success(function(dataFromServer, status,
					headers, config) {
				if (dataFromServer) {
					//validated go ahead
					$location.url('/home');
				}else{
					$rootScope.OffNavTabs = true;
				    $rootScope.OffLogoutTabs = true;
				    //commtinue
				}
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$scope.loginform_error = "Invalid request";
			$location.url('/');
			return $q.reject(response);
		}
	});
	
	//map code 
	$scope.markers = [];
	$scope.map = {
			center : {
				latitude : 45,
				longitude : -73
			},
			zoom : 11
		};
	
	//get markers from marker table and load it
	var response = $http.get("../../api/v1/locations");

    
	response
			.success(function(dataFromServer, status,
					headers, config) {
				if (dataFromServer.message == null
						|| dataFromServer.message == "") {
					
					//$scope.icon = "http://icons.iconarchive.com/icons/icons-land/vista-map-markers/256/Map-Marker-Marker-Outside-Azure-icon.png";
					for (var i = 0; i < dataFromServer.length; i++) {
							var marker = {
								id : dataFromServer[i].location_id,
								location_name:dataFromServer[i].location_name,
								className : 'marker'+dataFromServer[i].location_id,
								coords : {
									latitude : dataFromServer[i].latitude,
									longitude : dataFromServer[i].longitude
								},
								options : {
									draggable : true,
									title : dataFromServer[i].location_name,
									icon : 'http://localhost:8080/bikeshare/images/cycling.png'
								},
								events : {
									click : function(marker, eventName, args) {}
								}
								
							};
							
							$scope.markers.push(marker);
	                }
				}
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$scope.loginform_error = "Invalid request";
			$location.url('/');
			return $q.reject(response);
		}
	});
	
	$scope.getLocation = function() {
		if (navigator.geolocation) {

			var onSuccess = function(position) {
				$scope.map.center = {
					latitude : position.coords.latitude,
					longitude : position.coords.longitude
				};
				$scope.$apply();
			}
			function onError(error) {
			}
			navigator.geolocation.getCurrentPosition(onSuccess,
					onError);

			/*
			 * navigator.geolocation.getCurrentPosition(function(position) {
			 * 
			 * $scope.map = {center: {latitude:
			 * position.coords.latitude, longitude:
			 * position.coords.longitude }, zoom: 14 };
			 *  }, function() { handleNoGeolocation(true); });
			 */
		} else {
			// Browser doesn't support Geolocation
			handleNoGeolocation(false);
		}

	};
	$scope.googlemap = "googlemap.html";
	
});


bikeshareapp.controller('bookingdetailsCancelController', function($scope, $rootScope, $http, $location) {
	// create a message to display in our view
	
	//session validation
	var response = $http.get("../../api/v1/loggedin");
	response.success(function(dataFromServer, status,
					headers, config) {
				if (dataFromServer) {
					//validated go ahead
					$location.url('/bookingdetails');
				}else{
					$rootScope.OffNavTabs = true;
				    $rootScope.OffLogoutTabs = true;
					$location.url('/');
				}
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$scope.loginform_error = "Invalid request";
			$location.url('/');
			return $q.reject(response);
		}
	});
	
});



//create the controller and inject Angular's $scope
bikeshareapp.controller('logoutController', function($scope, $http, $rootScope) {
	// create a message to display in our view
	$rootScope.OffNavTabs = true;
    $rootScope.OffLogoutTabs = true;
    
   
	var data = {};
	var response = $http.post("../../api/v1/logout", data,
			{});
    
	response
			.success(function(dataFromServer, status,
					headers, config) {
				if (dataFromServer == true) {
					 
				}
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$scope.loginform_error = "Invalid request";
			$location.url('/');
			return $q.reject(response);
		}
	});
	
	//map code 
	$scope.markers = [];
	$scope.map = {
			center : {
				latitude : 45,
				longitude : -73
			},
			zoom : 13
		};
	
	//get markers from marker table and load it
	var response = $http.get("../../api/v1/locations");

    
	response
			.success(function(dataFromServer, status,
					headers, config) {
				if (dataFromServer.message == null
						|| dataFromServer.message == "") {
					
					//$scope.icon = "http://icons.iconarchive.com/icons/icons-land/vista-map-markers/256/Map-Marker-Marker-Outside-Azure-icon.png";
					for (var i = 0; i < dataFromServer.length; i++) {
							var marker = {
								id : dataFromServer[i].location_id,
								location_name:dataFromServer[i].location_name,
								className : 'marker'+dataFromServer[i].location_id,
								coords : {
									latitude : dataFromServer[i].latitude,
									longitude : dataFromServer[i].longitude
								},
								options : {
									draggable : true,
									title : dataFromServer[i].location_name,
									icon : 'http://localhost:8080/bikeshare/images/cycling.png'
								},
								events : {
									click : function(marker, eventName, args) {}
								}
								
							};
							
							$scope.markers.push(marker);
	                }
				}
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$scope.loginform_error = "Invalid request";
			$location.url('/');
			return $q.reject(response);
		}
	});
	
	$scope.getLocation = function() {
		if (navigator.geolocation) {

			var onSuccess = function(position) {
				$scope.map.center = {
					latitude : position.coords.latitude,
					longitude : position.coords.longitude
				};
				$scope.$apply();
			}
			function onError(error) {
			}
			navigator.geolocation.getCurrentPosition(onSuccess,
					onError);

			/*
			 * navigator.geolocation.getCurrentPosition(function(position) {
			 * 
			 * $scope.map = {center: {latitude:
			 * position.coords.latitude, longitude:
			 * position.coords.longitude }, zoom: 14 };
			 *  }, function() { handleNoGeolocation(true); });
			 */
		} else {
			// Browser doesn't support Geolocation
			handleNoGeolocation(false);
		}

	};
	$scope.googlemap = "googlemap.html";
	
});


bikeshareapp.controller('signupController', function($scope) {
});


bikeshareapp.controller('receiptController', function($scope, dataSharing, $http, $rootScope) {
	
	//session validation
	var response = $http.get("../../api/v1/loggedin");
	response.success(function(dataFromServer, status,
					headers, config) {
				if (dataFromServer) {
					//validated go ahead
				}else{
					$rootScope.OffNavTabs = true;
				    $rootScope.OffLogoutTabs = true;
					$location.url('/');
				}
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$scope.loginform_error = "Invalid request";
			$location.url('/');
			return $q.reject(response);
		}
	});
	
	$rootScope.OffNavTabs = false;
	$rootScope.OffLogoutTabs = false;
	
	$scope.booking_id_receipt = dataSharing.get().booking_id_receipt;
	$scope.location_name_receipt = dataSharing.get().location_name_receipt;
	$scope.time_receipt = dataSharing.get().time_receipt;
	$scope.bike_details = dataSharing.get().bike_details;
	$scope.price = dataSharing.get().price;
	$scope.freeRideFlag = dataSharing.get().freeFlag;
	
});

bikeshareapp.controller('loginformcontroller', function($scope, $http,
		$location, $q) {
	$scope.loginform_getAuthenticated = function(item, event) {
		var data = {
			username : $scope.loginform_username,
			password : $scope.loginform_password
		};
		var response = $http.post("../../api/v1/login", data, {});
		response.success(function(dataFromServer, status, headers, config) {
			if (dataFromServer.sessionId != null) {
				$location.url('/home');
			} else {
				$scope.loginform_error = "Invalid Username/Password";
			}

		});
		response.error(function(data, status, headers, config) {
			if (response.status === 401 || response.status === 400) {
				$scope.loginform_error = "Invalid Username/Password";
				$location.url('/');
				return $q.reject(response);
			}
		});
	};
});

bikeshareapp
		.controller(
				'authenticatecontroller',
				function($scope, $filter, $http, $location, dataSharing, $rootScope, $anchorScroll, $rootScope) {
					
					$scope.showNoBikesMessage = false;
					
					//session validation
					var response = $http.get("../../api/v1/loggedin");
					response.success(function(dataFromServer, status,
									headers, config) {
								if (dataFromServer) {
									//validated go ahead
								}else{
									$rootScope.OffNavTabs = true;
								    $rootScope.OffLogoutTabs = true;
									$location.url('/');
								}
							});
					response.error(function(data, status, headers, config) {
						if (response.status === 401
								|| response.status === 400) {
							$scope.loginform_error = "Invalid request";
							$location.url('/');
							return $q.reject(response);
						}
					});
					
					
					$scope.markers = [];
					
					$rootScope.OffNavTabs = false;
					$rootScope.OffLogoutTabs = false;
					
					$scope.displayForm = true;
					$scope.bikesListShow = true;
					$scope.bikeBookButtonShow = true;

					$scope.map = {
						center : {
							latitude : 45,
							longitude : -73
						},
						zoom : 13
					};
					
					

					/*
					 * $scope.map = { center : { latitude : 45, longitude : -73 },
					 * zoom : 14, control: {}, marker: {} };
					 */

					$scope.searchMapOnZipCode = function(zipcode) {
						var lat = '';
						var lng = '';
						var address = zipcode;
						geocoder = new google.maps.Geocoder();
						geocoder
								.geocode(
										{
											'address' : address + ', SAN JOSE, CA, US'
										},
										function(results, status) {
											if (status == google.maps.GeocoderStatus.OK) {
												// $scope.map.control.getGMap().setCenter(results[0].geometry.location);
												lat = results[0].geometry.location
														.lat();
												lng = results[0].geometry.location
														.lng();
												$scope.map.center = {
													latitude : lat,
													longitude : lng
												};
												$scope.$apply();

												// $scope.map = {center:
												// {latitude: lat, longitude:
												// lng }, zoom: 14 };
												$scope.map.setZoom(13);
											} else {
												cosole.log('Geocode was not successful for the following reason: '
														+ status);
											}
										});
					};

					
					
					
					//get markers from marker table and load it
					var response = $http.get("../../api/v1/locations");
				
			        
					response
							.success(function(dataFromServer, status,
									headers, config) {
								if (dataFromServer.message == null
										|| dataFromServer.message == "") {
									
									//$scope.icon = "http://icons.iconarchive.com/icons/icons-land/vista-map-markers/256/Map-Marker-Marker-Outside-Azure-icon.png";
									for (var i = 0; i < dataFromServer.length; i++) {
											var marker = {
												id : dataFromServer[i].location_id,
												location_name:dataFromServer[i].location_name,
												className : 'marker'+dataFromServer[i].location_id,
												coords : {
													latitude : dataFromServer[i].latitude,
													longitude : dataFromServer[i].longitude
												},
												options : {
													draggable : true,
													title : dataFromServer[i].location_name,
													icon : 'http://localhost:8080/bikeshare/images/cycling.png'
												},
												events : {
													click : function(marker, eventName, args) {
														$scope.showNoBikesMessage = false;
														$scope.bikesListShow = true;
														resetDropDown();														
														var lat = Number(marker.getPosition().lat().toPrecision(8));
												        var lon = Number(marker.getPosition().lng().toPrecision(8));
												        
												        
												        
												        for(var i = 0; i < $scope.markers.length; i++){
												        	
												        	var latm = Number($scope.markers[i].coords.latitude).toPrecision(8);
															var lonm = Number($scope.markers[i].coords.longitude).toPrecision(8);
												        	
												        	if(lat == latm && lon == lonm){
												        		$scope.location_id = $scope.markers[i].id;
																$scope.location_name = dataFromServer[i].location_name;
												        	}
												        }
												        $scope.displayForm = false;
												        
												        //set current date
												        $scope.booking_date = "Booking Date: "+$filter('date')(Date.now(), 'MM/dd/yyyy');
												        
												        
														// set from time drop down
														$scope.timelist = [];
														var j = 0;
														for (i = $filter('date')(Date.now(), 'H'); i <= 24; i++) {
															var timetext;
															if(i < 12 || i == 24){
																timetext = i % 24+":00 AM"
															}else{
																if( i == 12 ) timetext = "12:00 PM"
																else{
																	timetext = (i%12)+":00 PM"
																}
															}
															
															$scope.timelist[j] = {
																value : i,
																text : timetext
															};
															j = j + 1;
														}
														// $location.hash('bookingsection');
														// $anchorScroll();
														//$(this).scrollspy('scroll-able')
														scroll();
													}
												}
												
											};
											
											$scope.markers.push(marker);
					                }
								}
							});
					response.error(function(data, status, headers, config) {
						if (response.status === 401
								|| response.status === 400) {
							$scope.loginform_error = "Invalid request";
							$location.url('/');
							return $q.reject(response);
						}
					});
					
					/*// markers
					$scope.marker = {
						id : 0,
						coords : {
							latitude : 37.3357468,
							longitude : -121.8842035
						},
						options : {
							draggable : true
						},
						events : {
							click : function(marker, eventName, args) {

								$scope.location_id = "95115";
								$scope.location_name = "san jose state university";

								// set from time drop down
								$scope.timelist = [];
								var j = 0;
								for (i = $filter('date')(Date.now(), 'H'); i <= 24; i++) {
									$scope.timelist[j] = {
										value : i,
										text : i
									};
									j = j + 1;
								}

								$scope.displayForm = false;
							}
						}
					};*/

					$scope.unlockToTime = function($fromtimeVal) {
						resetDropDown();
						// set to time drop down
						$scope.totimelist = [];
						var j = 0;
						for (i = 1 + parseInt($fromtimeVal.value); i < 25; i++) {
							var timetext;
							if(i < 12 || i == 24){
								timetext = i % 24+":00 AM"
							}else{
								if( i == 12 ) timetext = "12:00 PM"
								else{
									timetext = (i%12)+":00 PM"
								}
							}
							
							$scope.totimelist[j] = {
								value : i,
								text : timetext
							};
							j = j + 1;
						}
						
					};

					// fetch availability
					$scope.checkAvailability = function($location_id,
							$fromtime, $totime) {

						// call availability service
						var data = {
							location_id : $location_id,
							fromHour : $fromtime.value,
							toHour : $totime.value
						};
						var response = $http.post("../../api/v1/availability",
								data, {});
						response
								.success(function(dataFromServer, status,
										headers, config) {
									if (dataFromServer!=null && dataFromServer.length == 0 ) {
										$scope.showNoBikesMessage = true;
										$scope.bikesListShow = true;
										//$scope.bookingform_error = "No Bikes Available. Please Select another locaion or another time slot";
									} else {
										if ((dataFromServer.message == null  || dataFromServer.message == "") && dataFromServer !=null) {
											$scope.bikeslist = dataFromServer;
											$scope.bikesListShow = false;
											$scope.showNoBikesMessage = false;
										} else {
											$scope.bookingform_error = dataFromServer.message;
										}
									}
								});
						response.error(function(data, status, headers, config) {
							if (response.status === 401
									|| response.status === 400) {
								$scope.bookingform_error = "Invalid request";
								$location.url('/');
								return $q.reject(response);
							}
						});
					};

					// confirm booking
					$scope.confirmBooking = function($location_id, $fromtime,
							$totime, $bikeid) {

						// confirm booking data
						var data = {
							location_id : $location_id,
							fromHour : $fromtime.value,
							toHour : $totime.value,
							bike_id : $bikeid.bike_id
						};
						var response = $http.post("../../api/v1/reserve", data,
								{});
						response
								.success(function(dataFromServer, status,
										headers, config) {
									if (dataFromServer.message == null
											|| dataFromServer.message == "") {
										var dataForReceipt = {
												booking_id_receipt : dataFromServer.transaction_id,
												location_name_receipt : $scope.location_name,
												time_receipt :  $scope.fromtime.text + " - " +$scope.totime.text,
												bike_details : $scope.bikeid.bikename + " " + $scope.bikeid.type,
												price : "$"+ ($scope.totime.value - $scope.fromtime.value) * $scope.bikeid.price,
												freeFlag : dataFromServer.freeRide
											};
										dataSharing.set(dataForReceipt);
										$location.url('/receipt');
									} else {
										$scope.bookingform_error = dataFromServer.message;
									}
								});
						response.error(function(data, status, headers, config) {
							if (response.status === 401
									|| response.status === 400) {
								$scope.bookingform_error = "Invalid request";
								$location.url('/');
								return $q.reject(response);
							}
						});

					};

					$scope.showConfirmBookingButton = function() {
						$scope.bikeBookButtonShow = false;
						
					};

					/*
					 * if(navigator.geolocation) {
					 * navigator.geolocation.getCurrentPosition(function(position) {
					 * 
					 * $scope.map = {center: {latitude:
					 * position.coords.latitude, longitude:
					 * position.coords.longitude }, zoom: 10 };
					 *  }, function() { handleNoGeolocation(true); }); } else { //
					 * Browser doesn't support Geolocation
					 * handleNoGeolocation(false); }
					 */

					$scope.getLocation = function() {
						if (navigator.geolocation) {

							var onSuccess = function(position) {
								$scope.map.center = {
									latitude : position.coords.latitude,
									longitude : position.coords.longitude
								};
								$scope.$apply();
							}
							function onError(error) {
							}
							navigator.geolocation.getCurrentPosition(onSuccess,
									onError);

							/*
							 * navigator.geolocation.getCurrentPosition(function(position) {
							 * 
							 * $scope.map = {center: {latitude:
							 * position.coords.latitude, longitude:
							 * position.coords.longitude }, zoom: 14 };
							 *  }, function() { handleNoGeolocation(true); });
							 */
						} else {
							// Browser doesn't support Geolocation
							handleNoGeolocation(false);
						}

					};
					$scope.googlemap = "googlemap.html";


				
					
				});

bikeshareapp
		.controller(
				'signupformcontroller',
				function($scope, $http, $location, $q) {
					$scope.signupform_signup = function(item, event) {
						
						var data = {
							name : $scope.signupform_name,
							email : $scope.signupform_email,
							phone : $scope.signupform_phone,
							address : $scope.signupform_address,
							zipcode : $scope.signupform_zipcode,
							username : $scope.signupform_username,
							password : $scope.signupform_password
						};
						var response = $http.post("../../api/v1/users", data,
								{});
						response
								.success(function(dataFromServer, status,
										headers, config) {
									if (dataFromServer.message == null
											|| dataFromServer.message == "") {
										$scope.signupform_success = "Congrats "+$scope.signupform_name+". You are successfully registered with us!";
									} else {
										$scope.signupform_error = dataFromServer.message;
									}
								});
						response.error(function(data, status, headers, config) {
							if (response.status === 401
									|| response.status === 400) {
								$scope.loginform_error = "Invalid request";
								$location.url('/');
								return $q.reject(response);
							}
						});
					};
				});


/*bikeshareapp
.controller(
		'bookingdetailsController',
		function($scope, $http, $location, $q, dataSharing, $timeout) {
			var displayData;
			 $scope.isLoading = false;
			var response = $http.get("../../api/v1/transactions");
			response
					.success(function(dataFromServer, status,
							headers, config) {
						if (dataFromServer.message == null
								|| dataFromServer.message == "") {
							$scope.transactionlist = dataFromServer;
							displayData = dataFromServer;
							$scope.itemsByPage=1;
						}
					});
			response.error(function(data, status, headers, config) {
				if (response.status === 401
						|| response.status === 400) {
					$scope.loginform_error = "Invalid request";
					$location.url('/');
					return $q.reject(response);
				}
			});
			
			$scope.callServer = function getData(tableState, tableController) {

				//here you could create a query string from tableState
				//fake ajax call
				$scope.isLoading = true;

				$timeout(function () {
					getAPage();
						$scope.isLoading = false;
				}, 2000);
	
		    };
		
		    
		    function getAPage() {
		    	$scope.transactionlist = displayData;
		    }
		    getAPage();

			
		});*/

bikeshareapp
.controller(
		'bookingdetailsController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
			$rootScope.OffNavTabs = false;
			$rootScope.OffLogoutTabs = false;
			
			//session validation
			var response = $http.get("../../api/v1/loggedin");
			response.success(function(dataFromServer, status,
							headers, config) {
						if (dataFromServer) {
							//validated go ahead
						}else{
							$rootScope.OffNavTabs = true;
						    $rootScope.OffLogoutTabs = true;
							$location.url('/');
						}
					});
			response.error(function(data, status, headers, config) {
				if (response.status === 401
						|| response.status === 400) {
					$scope.loginform_error = "Invalid request";
					$location.url('/');
					return $q.reject(response);
				}
			});
			
			
			var response = $http.get("../../api/v1/transactions");

	        $scope.queue = {
	            transactions: []
	        };
	        
			response
					.success(function(dataFromServer, status,
							headers, config) {
						if (dataFromServer.message == null
								|| dataFromServer.message == "") {
							 for (var i = 0; i < dataFromServer.length; i++) {
			                        $scope.queue.transactions.push(dataFromServer[i]);
			                }
							$scope.itemsByPage=10;
						}
					});
			response.error(function(data, status, headers, config) {
				if (response.status === 401
						|| response.status === 400) {
					$scope.loginform_error = "Invalid request";
					$location.url('/');
					return $q.reject(response);
				}
			});

			
			$scope.cancelBooking = function(booking_id){
				
				//session validation
				var response = $http.get("../../api/v1/loggedin");
				response.success(function(dataFromServer, status,
								headers, config) {
							if (dataFromServer) {
								//validated go ahead
							}else{
								$rootScope.OffNavTabs = true;
							    $rootScope.OffLogoutTabs = true;
								$location.url('/');
							}
						});
				response.error(function(data, status, headers, config) {
					if (response.status === 401
							|| response.status === 400) {
						$scope.loginform_error = "Invalid request";
						$location.url('/');
						return $q.reject(response);
					}
				});
				
				
				// confirm booking data
				var data = {
						transaction_id : booking_id
				};
				var response = $http.post("../../api/v1/cancel", data,
						{});
				response
						.success(function(dataFromServer, status,
								headers, config) {
							if (dataFromServer.cancel_success) {
								$location.url('/bookingdetailscancel');
							} else {
								$scope.bookingform_error = dataFromServer.message;
							}
						});
				response.error(function(data, status, headers, config) {
					if (dataFromServer.cancel_success) {
						$scope.bookingform_error = "Invalid request";
						$location.url('/');
						return $q.reject(response);
					}
				});
			};
			
		});


	


