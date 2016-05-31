'use strict';

angular
		.module('hotelApp')
		.controller(
				'BillController',
				function($scope, $state, Bill, ParseLinks, Method_payment,
						Status_payment, Method_register, Status_bill, $filter,
						Room, Customer) {
					$scope.fromDate=$scope.toDate=new Date();
					$scope.method_payments = Method_payment.query();
					$scope.status_payments = Status_payment.query();
					$scope.method_registers = Method_register.query();
					$scope.status_bills = Status_bill.query();
					$scope.rooms = Room.query();
					$scope.customers = Customer.query();
					$scope.bills = [];
					$scope.predicate = 'id';
					$scope.reverse = true;
					$scope.page = 0;
					$scope.size = 10;
					$scope.customer = {};
					$scope.room = {}
					$scope.customer.selected = undefined;
					$scope.room.selected = undefined;
					$scope.defaultvalue = {
						id : 0
					};
					function checkNull() {
						if (!$scope.method_payment) {
							$scope.method_payment = $scope.defaultvalue;
						}
						if (!$scope.status_payment) {
							$scope.status_payment = $scope.defaultvalue;
						}
						if (!$scope.method_register) {
							$scope.method_register = $scope.defaultvalue;
						}
						if (!$scope.status_bill) {
							$scope.status_bill = $scope.defaultvalue;
						}
						if(!$scope.room.selected){
							$scope.room.selected=$scope.defaultvalue;
						}
						if(!$scope.customer.selected){
							$scope.customer.selected=$scope.defaultvalue;
						}
					}

					$scope.loadAll = function() {
						checkNull();
						console.log($scope.predicate + $scope.reverse)
						Bill.findAllByMultiAttr({
							fromDate:$filter('date')($scope.fromDate,'yyyy-MM-dd'),
							toDate:$filter('date')($scope.toDate,'yyyy-MM-dd'),
							room:$scope.room.selected.id,
							customer:$scope.customer.selected.id,
							method_payment:$scope.method_payment.id,
							status_payment:$scope.status_payment.id,
							method_register:$scope.method_register.id,
							status_bill:$scope.status_bill.id,
							page : $scope.page - 1,
							size : $scope.size,
							sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]},
										function(result, headers) {
											$scope.links = ParseLinks
													.parse(headers('link'));
											$scope.totalItems = headers('X-Total-Count');
											$scope.bills = result;
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
						$scope.bill = {
							fees_room : null,
							fees_service : null,
							fees_other : null,
							fees_bonus : null,
							total : null,
							fees_vat : null,
							total_vat : null,
							decription : null,
							create_date : null,
							id : null
						};
					};
					
					$scope.clearCustomer = function() {
						$scope.customer.selected = undefined;
					}

					$scope.clearRoom = function() {
						$scope.room.selected = undefined;
					}

					$scope.exportExcel = function(){
						checkNull();
						window.open("api/bills/exportExcel?fromDate="+$filter('date')($scope.fromDate,'yyyy-MM-dd')+
								"&toDate="+$filter('date')($scope.toDate,'yyyy-MM-dd')+
								"&room="+$scope.room.selected.id+
								"&customer="+$scope.customer.selected.id+
								"&method_payment="+$scope.method_payment.id+
								"&status_payment="+$scope.status_payment.id+
								"&method_register="+$scope.method_register.id+
								"&status_bill="+$scope.status_bill.id)
					}
					function initDatagrid(data) {
						$('#dg')
								.datagrid(
										{
											data : data,
											// pagination:true,
											rownumbers : true,
											fitColumns : true,
											singleSelect : true,
											collapsible : true,
											title : "DANH SÁCH ĐĂNG KÝ DỊCH VỤ TÒA NHÀ DMCTOWER",
											nowrap : true,
											showFooter : true,
											width : "100%",
											columns : [ [
													{
														field : 'id',
														title : 'Mã TT',
														width : 50,
														fixed : true
													},
													{
														field : 'fees_room',
														title : 'Phí phòng',
														width : 70,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(value, '',
																			0)
														}
													},
													{
														field : 'fees_service',
														title : 'Phí dv',
														width : 70,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(value, '',
																			0)
														}
													},
													{
														field : 'fees_bonus',
														title : 'Phí thưởng',
														width : 70,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(value, '',
																			0)
														}
													},
													{
														field : 'fees_other',
														title : 'Phí khác',
														width : 70,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(value, '',
																			0)
														}
													},
													{
														field : 'deposit',
														title : 'Đã đặt cọc',
														width : 70,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(
																			row.reservation.register_info.deposit_value,
																			'',
																			0)
														}
													},
													{
														field : 'total',
														title : 'Tổng tiền',
														width : 70,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(value, '',
																			0)
														}
													},
													{
														field : 'fees_vat',
														title : 'Phí VAT',
														width : 70,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(value, '',
																			0)
														}
													},
													{
														field : 'total_vat',
														title : 'Tổng tiền(VAT)',
														width : 75,
														align : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'currency')
																	(value, '',
																			0)
														}
													},
													{
														field : 'currency',
														title : 'ĐVT',
														width : 40,
														fixed : true,
														formatter : function(
																value, row,
																index) {
															if (value) {
																return value.code;
															} else {
																return "";
															}
														}
													},
													{
														field : 'method_payment',
														title : 'Phương thức TT',
														width : 120,
														fixed : true,
														alight : "right",
														formatter : function(
																value, row,
																index) {
															if (value) {
																return value.name;
															} else {
																return "";
															}
														}
													},
													{
														field : 'status_payment',
														title : 'Trạng thái TT',
														width : 100,
														fixed : true,
														alight : "right",
														formatter : function(
																value, row,
																index) {
															if (value) {
																return value.name;
															} else {
																return "";
															}
														}
													},
													{
														field : 'reservation',
														title : 'Mã nhận phòng',
														width : 50,
														alight : "right",
														fixed : true,
														formatter : function(
																value, row,
																index) {
															if (value) {
																return value.id;
															} else {
																return "";
															}
														}
													},
													{
														field : 'decription',
														title : 'Mô tả',
														width : 120,
														fixed : true
													},
													{
														field : 'status_bill',
														title : 'Trạng thái',
														width : 100,
														fixed : true,
														alight : "right",
														formatter : function(
																value, row,
																index) {
															if (value) {
																return value.name;
															} else {
																return "";
															}
														}
													},
													{
														field : 'create_date',
														title : 'Ngày tạo',
														width : 120,
														fixed : true,
														formatter : function(
																value, row,
																index) {
															return $filter(
																	'date')
																	(value,
																			'dd/MM/yyyy hh:MM');
														}
													} ] ],
											onClickRow : function(index, row) {
												$scope.Selected = row;
											},
											onDblClickRow : function(index, row) {
												$state.go('reservation.detail',
														row.reservation);
											},
											detailFormatter : function(
													rowIndex, rowData) {
												return "help";
											}
										});
						$('#dg').datagrid({
			               	toolbar: [{
			   	             		iconCls: 'icon-print',
			   	             		handler: function(){
				   	             		if(!$scope.Selected){
			   	             				alert("Bạn chưa chọn phiếu thanh toán!");
			   	             				return false;
			   	             			}
			   	             			$state.go('bill.printer',$scope.Selected);
			   	             		}
			   	             	}
			               	]
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
