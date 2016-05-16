'use strict';

angular.module('hotelApp').controller(
		'Register_infoController',
		function($scope, $state, Register_info, ParseLinks, Method_payment,
				Status_payment, Method_register, Status_register,$filter) {

			$scope.method_payments = Method_payment.query();
			$scope.status_payments = Status_payment.query();
			$scope.method_registers = Method_register.query();
			$scope.status_registers = Status_register.query();

			$scope.register_infos = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 0;
	        $scope.size=10;
			$scope.defaultvalue={
					id:0
			};
			function checkNull(){
				if(!$scope.method_payment){
					$scope.method_payment=$scope.defaultvalue;
				}
				if(!$scope.status_payment){
					$scope.status_payment=$scope.defaultvalue;
				}
				if(!$scope.method_register){
					$scope.method_register=$scope.defaultvalue;
				}
				if(!$scope.status_register){
					$scope.status_register=$scope.defaultvalue;
				}
			}
			$scope.code="";
			$scope.ipnumber="";
//			$scope.fromDate=new Date();
//			$scope.toDate=new Date();
			
			$scope.loadAll = function() {
				checkNull();
				Register_info.findAllByMultiAttr({
					fromDate:$filter('date')($scope.fromDate,'yyyy-MM-dd'),
					toDate:$filter('date')($scope.toDate,'yyyy-MM-dd'),
					code:$scope.code,
					ipnumber:$scope.ipnumber,
					method_payment:$scope.method_payment.id,
					status_payment:$scope.status_payment.id,
					method_register:$scope.method_register.id,
					status_register:$scope.status_register.id,
					page : $scope.page - 1,
					size : $scope.size,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.register_infos = result;
					initDatagrid(result);
					createPagination($scope.totalItems);
				});
			};
			$scope.loadPage = function(page,size) {
				$scope.page = page;
				$scope.size=size;
				$scope.loadAll();
			};
			$scope.loadAll();

			$scope.refresh = function() {
				$scope.loadAll();
				$scope.clear();
			};

			$scope.clear = function() {
				$scope.register_info = {
					date_checkin : null,
					date_checkout : null,
					number_of_adult : null,
					number_of_kid : null,
					other_request : null,
					deposit_value : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
			
			$scope.exportExcel = function(){
				checkNull();
				window.open("api/register_infos/exportExcel?fromDate="+$filter('date')($scope.fromDate,'yyyy-MM-dd')+
					"&toDate="+$filter('date')($scope.toDate,'yyyy-MM-dd')+
					"&code="+$scope.code+
					"&ipnumber="+$scope.ipnumber+
					"&method_payment="+$scope.method_payment.id+
					"&status_payment="+$scope.status_payment.id+
					"&method_register="+$scope.method_register.id+
					"&status_register="+$scope.status_register.id);
			}
			
			function initDatagrid(data){
		       	 $('#dg').datagrid({
		       		 data:data,
		// pagination:true,
		                rownumbers:true,
		                fitColumns:true,
		                singleSelect:true,
		                collapsible:true,
		                title:"DANH SÁCH ĐĂNG KÝ ĐẶT PHÒNG TÒA NHÀ DMCTOWER",
		                nowrap:true,
		                columns:[[
		                    {field:'id',title:'Mã ĐK',width:50,fixed:true},
		                    {field:'date_checkin',title:'Ngày đến',width:80,fixed:true},
		                    {field:'date_checkout',title:'Ngày đi',width:80,fixed:true},
		                    {field:'number_of_adult',title:'SLNL',width:40,fixed:true},
		                    {field:'number_of_kid',title:'SLTE',width:40,fixed:true},
		                    {field:'deposit_value',title:'Đặt cọc',width:80,alight:"right",fixed:true,
		                    	formatter: function(value,row,index){
		             				return $filter('currency')(value,'',0)
		             			}
		                    },
		                    {field:'currency',title:'ĐVT',width:40,fixed:true,
		                    	formatter: function(value,row,index){
		                    		 if (value){
		              					return value.code;
		              				} else {
		              					return "";
		              				}
		                		}
		                    },
		                    {field:'room',title:'Mã phòng',width:50,fixed:true,alight:"right",
		                    	formatter: function(value,row,index){
		                      		 if (value){
		                					return value.code;
		                				} else {
		                					return "";
		                				}
		                  		}
		                    },
		                    {field:'customer',title:'Tên khách hàng',width:150,fixed:true,
		                    	formatter: function(value,row,index){
		                     		 if (value){
		               					return value.full_name;
		               				} else {
		               					return "";
		               				}
		                 		}
		                    },
		                    {field:'method_payment',title:'PTTT',width:120,fixed:true,
		                    	formatter: function(value,row,index){
		                    		 if (value){
		              					return value.name;
		              				} else {
		              					return "";
		              				}
		                		}
		                    },
		                    {field:'status_payment',title:'TTTT',width:100,fixed:true,
		                    	formatter: function(value,row,index){
		                    		 if (value){
		              					return value.name;
		              				} else {
		              					return "";
		              				}
		                		}
		                    },
		                    {field:'method_register',title:'PTDK',width:150,fixed:true,
		                    	formatter: function(value,row,index){
		                    		 if (value){
		              					return value.name;
		              				} else {
		              					return "";
		              				}
		                		}
		                    },
		                    {field:'status_register',title:'TTDK',width:150,fixed:true,
		                    	formatter: function(value,row,index){
		                    		 if (value){
		              					return value.name;
		              				} else {
		              					return "";
		              				}
		                		}
		                    },
		                    {field:'create_date',title:'Ngày tạo',width:120,fixed:true,
			                   	formatter: function(value,row,index){
			                   		return $filter('date')(value,'dd/MM/yyyy hh:mm:ss');
			               		}
		                    }
		                ]],
		                onClickRow:function(index,row){
		                  	$scope.Selected=row;
		               },
		               onDblClickRow:function(index,row){
		                 	 $state.go('register_info.detail',row);
		               },
		               detailFormatter: function(rowIndex, rowData){
		               	return "help";
		               }
		            });
		            $('#dg').datagrid({
		            	toolbar: [{
			             		iconCls: 'icon-add',
			             		handler: function(){
			             			$state.go('register_info.new');
			             		}
			             	},'-',{
			             		iconCls: 'icon-edit',
			             		handler: function(){
			             			if(!$scope.Selected){
			             				alert("Bạn chưa chọn phòng!");
			             				return false;
			             			}
			             			$state.go('register_info.edit',$scope.Selected);
			             		}
			             	},'-',{
			             		iconCls: 'icon-remove',
			             		handler: function(){
			             			if(!$scope.Selected){
			             				alert("Bạn chưa chọn phòng!");
			             				return false;
			             			}
			             			$state.go('register_info.delete',$scope.Selected);
			             		}
			             	}
			             	,'-',{
			             		iconCls: 'icon-help',
			             		handler: function(){
			             			if(!$scope.Selected){
			             				alert("Bạn chưa chọn phòng!");
			             				return false;
			             			}
			             			$state.go('register_info.detail',$scope.Selected);
			             		}
			             	}
		            	]
		            });
		       }
			function createPagination(totalItem){
				$('#pp').pagination({
		            total:totalItem,
		            pageSize:$scope.size,
		            onSelectPage:function(pageNumber, pageSize){
		        		$(this).pagination('loading');
		        		$scope.loadPage(pageNumber,pageSize);
		        		$(this).pagination('loaded');
		        	}
		        });
			}
		});
