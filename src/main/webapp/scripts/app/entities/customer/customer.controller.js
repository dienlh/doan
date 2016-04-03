'use strict';

angular.module('hotelApp').controller(
		'CustomerController',
		function($scope, $state, Customer, ParseLinks, $filter) {

			$scope.customers = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 0;
			$scope.size = 10;
			$scope.email = "";
			$scope.ic_number = "";
			$scope.loadAll = function() {
				Customer.findAllByIcPassportNumberAndEmail({
					// email:$scope.email,
					ipnumber : $scope.ic_number,
					page : $scope.page - 1,
					size : 10,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.customers = result;
					initDatagrid(result);
					createPagination($scope.totalItems);
				});
			};
			$scope.loadPage = function(page, size) {
				$scope.page = page;
				$scope.size = size;
				$scope.loadAll();
			};
			$scope.loadAll();

			$scope.refresh = function() {
				$scope.loadAll();
				$scope.clear();
			};

			$scope.clear = function() {
				$scope.customer = {
					full_name : null,
					ic_passport_number : null,
					ic_passport_prov_date : null,
					ic_passport_prov_add : null,
					email : null,
					phone_number : null,
					birthday : null,
					address : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};

			function initDatagrid(data) {
				$('#dg').datagrid({
					data : data,
					// pagination:true,
					rownumbers : true,
					fitColumns : true,
					singleSelect : true,
					collapsible : true,
					title : "DANH SÁCH KHÁCH HÀNG TÒA NHÀ DMCTOWER",
					nowrap : true,
					width : "100%",
					columns : [ [ {
						field : 'id',
						title : 'Mã KH',
						width : 50,
						fixed : true
					}, {
						field : 'full_name',
						title : 'Tên KH',
						width : 120,
						fixed : true
					}, {
						field : 'ic_passport_number',
						title : 'CMND/Passport',
						width : 100,
						fixed : true
					}, {
						field : 'ic_passport_prov_date',
						title : 'Ngày cấp',
						width : 80,
						fixed : true
					}, {
						field : 'ic_passport_prov_add',
						title : 'Nơi cấp',
						width : 120,
						fixed : true
					}, {
						field : 'email',
						title : 'Email',
						width : 100,
						fixed : true
					}, {
						field : 'phone_number',
						title : 'Điện thoại',
						width : 80,
						fixed : true
					}, {
						field : 'birthday',
						title : 'Ngày sinh',
						width : 80,
						alight : "right",
						fixed : true
					}, {
						field : 'address',
						title : 'Địa chỉ',
						width : 120,
						fixed : true
					}, {
						field : 'gender',
						title : 'Giới tính',
						width : 50,
						fixed : true,
						formatter : function(value, row, index) {
							if (value) {
								return value.name;
							} else {
								return "";
							}
						}
					}, {
						field : 'ethnic',
						title : 'Dân tộc',
						width : 50,
						fixed : true,
						formatter : function(value, row, index) {
							if (value) {
								return value.name;
							} else {
								return "";
							}
						}
					}, {
						field : 'religion',
						title : 'Tôn giáo',
						width : 80,
						fixed : true,
						formatter : function(value, row, index) {
							if (value) {
								return value.name;
							} else {
								return "";
							}
						}
					}, {
						field : 'company',
						title : 'Công ty',
						width : 100,
						fixed : true,
						formatter : function(value, row, index) {
							if (value) {
								return value.name;
							} else {
								return "";
							}
						}
					}, {
						field : 'create_date',
						title : 'Ngày tạo',
						width : 120,
						fixed : true,
						formatter : function(value, row, index) {
							return $filter('date')(value, 'dd/MM/yyyy hh:MM');
						}
					} ] ],
					onClickRow : function(index, row) {
						$scope.Selected = row;
					},
					onDblClickRow : function(index, row) {
						$state.go('customer.detail', row);
					},
					detailFormatter : function(rowIndex, rowData) {
						return "help";
					}
				});
				$('#dg').datagrid({
					toolbar : [ {
						iconCls : 'icon-add',
						handler : function() {
							$state.go('customer.new');
						}
					}, '-', {
						iconCls : 'icon-edit',
						handler : function() {
							if (!$scope.Selected) {
								alert("Bạn chưa chọn phòng!");
								return false;
							}
							$state.go('customer.edit', $scope.Selected);
						}
					}, '-', {
						iconCls : 'icon-remove',
						handler : function() {
							if (!$scope.Selected) {
								alert("Bạn chưa chọn phòng!");
								return false;
							}
							$state.go('customer.delete', $scope.Selected);
						}
					}, '-', {
						iconCls : 'icon-help',
						handler : function() {
							if (!$scope.Selected) {
								alert("Bạn chưa chọn phòng!");
								return false;
							}
							$state.go('customer.detail', $scope.Selected);
						}
					} ]
				});
			}

			function createPagination(totalItem) {
				$('#pp').pagination({
					total : totalItem,
					pageSize : $scope.size,
					onSelectPage : function(pageNumber, pageSize) {
						$(this).pagination('loading');
						$scope.loadPage(pageNumber, pageSize);
						$(this).pagination('loaded');
					}
				});
			}
		});
