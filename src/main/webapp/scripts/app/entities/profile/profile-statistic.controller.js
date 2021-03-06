'use strict';

angular.module('hotelApp').controller(
		'ProfileStatisticController',
		function($scope, $state, Profile, Position, Department, Status_profile,
				ParseLinks,$filter) {

			$scope.positions = Position.query();
			$scope.departments = Department.query();
			$scope.status_profiles = Status_profile.query();
			$scope.profiles = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			$scope.defaultvalue = {
				id : 0
			};
			$scope.full_name = "";

			function checkNull() {
				if (!$scope.position) {
					$scope.position = $scope.defaultvalue;
				}
				if (!$scope.department) {
					$scope.department = $scope.defaultvalue;
				}
				if (!$scope.status_profile) {
					$scope.status_profile = $scope.defaultvalue;
				}
			}
			$scope.loadAll = function() {
				checkNull();
				Profile.findByMultiAttrWithRanger({
					fromDate:$filter('date')($scope.fromDate,'yyyy-MM-dd'),
					toDate:$filter('date')($scope.toDate,'yyyy-MM-dd'),
					positionId : $scope.position.id,
					departmentId : $scope.department.id,
					statusId : $scope.status_profile.id,
					fullname : $scope.full_name,
					page : $scope.page - 1,
					size : 20,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.profiles = result;
				});
			};
			$scope.loadPage = function(page) {
				$scope.page = page;
				$scope.loadAll();
			};
//			$scope.loadAll();

			$scope.refresh = function() {
				$scope.loadAll();
				$scope.clear();
			};

			$scope.clear = function() {
				$scope.profile = {
					join_date : null,
					salary_subsidies : null,
					salary_basic : null,
					salary : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
			
			$scope.exportExcel = function(){
				checkNull();
				if($scope.fromDate==undefined || $scope.toDate == undefined){
					alert("Cần chọn khoảng ngày thống kê");
					return false;
				}
				window.open("api/profiles/exportExcel?fromDate="+$filter('date')($scope.fromDate,'yyyy-MM-dd')+
						"&toDate="+$filter('date')($scope.toDate,'yyyy-MM-dd')+
						"&positionId="+$scope.position.id+
						"&departmentId="+$scope.department.id+
						"&status="+$scope.status_profile.id)
			}
		});
