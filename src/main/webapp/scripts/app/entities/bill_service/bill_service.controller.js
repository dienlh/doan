'use strict';

angular.module('hotelApp').controller(
		'Bill_serviceController',
		function($scope, $state, Bill_service, ParseLinks, Room, Services,
				Status_bill_service,Reservation,$filter) {
			$scope.fromDate=$scope.toDate=new Date();
			$scope.rooms = Room.query();
			$scope.reservations=Reservation.query();
			$scope.status_bill_services = Status_bill_service.query();
			$scope.servicess = Services.query();
			$scope.bill_services = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 0;
	        $scope.size=10;
			$scope.defaultvalue={
					id:0
			};
			function checkNull(){
				if(!$scope.reservation){
					$scope.reservation=$scope.defaultvalue;
				}
				if(!$scope.service){
					$scope.service=$scope.defaultvalue;
				}
				if(!$scope.status_bill_service){
					$scope.status_bill_service=$scope.defaultvalue;
				}
			}
			$scope.loadAll = function() {
				checkNull();
				Bill_service.findAllByMultiAttr({
					fromDate:$filter('date')($scope.fromDate,'yyyy-MM-dd'),
					toDate:$filter('date')($scope.toDate,'yyyy-MM-dd'),
					serviceId : $scope.service.id, 
					statusId :$scope.status_bill_service.id, 
					reservationId : $scope.reservation.id,
					page : $scope.page - 1,
					size : $scope.size,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.bill_services = result;
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
				$scope.bill_service = {
					quantity : null,
					total : null,
					decription : null,
					create_date : null,
					id : null
				};
			};
			$scope.exportExcel = function(){
				window.open("api/bill_services/exportExcel?fromDate="+$filter('date')($scope.fromDate,'yyyy-MM-dd')+
						"&toDate="+$filter('date')($scope.toDate,'yyyy-MM-dd')+
						"&serviceId="+ $scope.service.id+
						"&statusId="+$scope.status_bill_service.id+ 
						"&reservationId="+ $scope.reservation.id);
			}
			function initDatagrid(data){
	          	 $('#dg').datagrid({
	          		 data:data,
	   // pagination:true,
	                   rownumbers:true,
	                   fitColumns:true,
	                   singleSelect:false,
	                   ctrlSelect:true,
	                   collapsible:true,
	                   width:"100%",
	                   title:"DANH SÁCH ĐĂNG KÝ DỊCH VỤ TÒA NHÀ DMCTOWER",
	                   nowrap:true,
	                   columns:[[
	                       {field:'id',title:'Mã DKDV',width:50,fixed:true},
	                       {field:'services',title:'Dịch vụ',width:120,fixed:true,
	                    	   formatter: function(value,row,index){
	                        		 if (value){
	                  					return value.name;
	                  				} else {
	                  					return "";
	                  				}
	                    		}
	                       },
	                       {field:'quantity',title:'Số lượng',width:80,fixed:true},
	                       {field:'total',title:'Tổng tiền',width:100,align:"right",fixed:true,
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
	                       {field:'decription',title:'Mô tả',width:120,fixed:true},
	                       {field:'reservation',title:'Mã nhận phòng',width:100,alight:"right",fixed:true,
	                    	   formatter: function(value,row,index){
	                        		 if (value){
	                  					return value.id;
	                  				} else {
	                  					return "";
	                  				}
	                    		}
	                       },
	                       {field:'code',title:'Mã phòng',width:100,alight:"right",fixed:true,
	                    	   formatter: function(value,row,index){
	                    		   	console.log(row);
	                  				return row.reservation.register_info.room.code;
	                    		}
	                       },
	                       {field:'status_bill_service',title:'Trạng thái',width:120,fixed:true,alight:"right",
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
	   	                   		return $filter('date')(value,'dd/MM/yyyy hh:MM');
	   	               		}
	                       }
	                   ]],
	                  onClickRow:function(index,row){
	                  	$scope.Selected=row;
	                  },
	                  detailFormatter: function(rowIndex, rowData){
	                  	return "help";
	                  }
	               });
	               $('#dg').datagrid({
	               	toolbar: [{
	   	             		iconCls: 'icon-add',
	   	             		handler: function(){
	   	             			$state.go('bill_service.new');
	   	             		}
	   	             	},'-',{
	   	             		iconCls: 'icon-edit',
	   	             		handler: function(){
	   	             			console.log($scope.Selected);
	   	             			if(!$scope.Selected){
	   	             				alert("Bạn chưa chọn phiếu dịch vụ!");
	   	             				return false;
	   	             			}else{
	   	             				if($scope.Selected.status_bill_service.id==1 
	   	             						|| $scope.Selected.status_bill_service.id==4
	   	             						|| $scope.Selected.reservation.status_reservation == 2
	   	             						|| $scope.Selected.reservation.status_reservation == 3){
		   	             				alert("Phiếu không được phép sửa");
		   	             				return false;
	   	             				}
	   	             			}
	   	             			$state.go('bill_service.edit',$scope.Selected);
	   	             		}
	   	             	},'-',{
	   	             		iconCls: 'icon-remove',
	   	             		handler: function(){
	   	             			if(!$scope.Selected){
	   	             				alert("Bạn chưa chọn phiếu dịch vụ!");
	   	             				return false;
	   	             			}else{
	   	             				if($scope.Selected.status_bill_service.id==1 
	   	             						|| $scope.Selected.status_bill_service.id==4
	   	             						|| $scope.Selected.reservation.status_reservation == 2
	   	             						|| $scope.Selected.reservation.status_reservation == 3){
		   	             				alert("Phiếu không được phép xóa");
		   	             				return false;
	   	             				}
	   	             			}
	   	             			$state.go('bill_service.delete',$scope.Selected);
	   	             		}
	   	             	}
	   	             	,'-',{
	   	             		iconCls: 'icon-print',
	   	             		handler: function(){
	   	             			$state.go('bill_service.printer',{
	   	             			fromDate:$filter('date')($scope.fromDate,'yyyy-MM-dd'),
		   	 					toDate:$filter('date')($scope.toDate,'yyyy-MM-dd'),
		   	 					serviceId : $scope.service.id, 
		   	 					statusId :$scope.status_bill_service.id, 
		   	 					reservationId : $scope.reservation.id,
	   	             			});
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
