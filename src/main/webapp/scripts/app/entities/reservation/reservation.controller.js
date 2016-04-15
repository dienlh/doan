'use strict';

angular.module('hotelApp')
    .controller('ReservationController', function ($scope, $state, Reservation, ParseLinks,$filter) {

        $scope.reservations = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.size=10;
        $scope.loadAll = function() {
            Reservation.query({page: $scope.page - 1, size: $scope.size, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.reservations = result;
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


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.reservation = {
                time_checkin: null,
                time_checkout: null,
                note_checkin: null,
                note_checkout: null,
                create_date: null,
                last_modified_date: null,
                id: null
            };
        };
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
                  title:"DANH SÁCH NHẬN TRẢ PHÒNG KHÁCH SẠN DMCTHOTEL",
                  nowrap:true,
                  columns:[[
                      {field:'id',title:'Mã nhận phòng',width:50,fixed:true},
                      {field:'time_checkin',title:'Thời gian nhận phòng',width:150,fixed:true,
                    	  	formatter: function(value,row,index){
    	                   		return $filter('date')(value,'dd/MM/yyyy hh:MM');
    	               		}
                      },
                      {field:'person_checkin',title:'Người nhận',width:120,fixed:true,
                    	  	formatter: function(value,row,index){
  	                   			return value.full_name;
  	               			}
                      },
                      {field:'time_checkout',title:'Thời gian trả phòng',width:150,fixed:true,
                    	  	formatter: function(value,row,index){
    	                   		return $filter('date')(value,'dd/MM/yyyy hh:MM');
    	               		}
                      },
                      {field:'person_checkout',title:'Người trả',width:120,fixed:true,
                    	  	formatter: function(value,row,index){
	                   			return value.full_name;
	               			}  
                      },
                      {field:'register_info',title:'Mã đăng ký',width:100,fixed:true,
	                   	   formatter: function(value,row,index){
	                       		 if (value){
	                 					return value.id;
	                 				} else {
	                 					return "";
	                 				}
	                   		}
                      },
                      {field:'Mã phòng',title:'Mã phòng',width:100,fixed:true,
                   	   formatter: function(value,row,index){
                   		   	console.log(row);
                 				return row.register_info.room.code;
                   		}
                      },
                      {field:'status_reservation',title:'Trạng thái',width:120,fixed:true,alight:"right",
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
                 onDblClickRow:function(index,row){
                 	 $state.go('reservation.detail',row);
                 },
                 detailFormatter: function(rowIndex, rowData){
                 	return "help";
                 }
              });
              $('#dg').datagrid({
              	toolbar: [{
  	             		iconCls: 'icon-add',
  	             		handler: function(){
  	             			$state.go('reservation.new');
  	             		}
  	             	},'-',{
  	             		iconCls: 'icon-edit',
  	             		handler: function(){
  	             			console.log($scope.Selected);
  	             			if(!$scope.Selected){
  	             				alert("Bạn chưa chọn phiếu dịch vụ!");
  	             				return false;
  	             			}else{
  	             				if($scope.Selected.status_reservation.id==2){
	   	             				alert("Phiếu không được phép sửa");
	   	             				return false;
  	             				}
  	             			}
  	             			$state.go('reservation.edit',$scope.Selected);
  	             		}
  	             	},'-',{
  	             		iconCls: 'icon-remove',
  	             		handler: function(){
  	             			if(!$scope.Selected){
  	             				alert("Bạn chưa chọn phiếu dịch vụ!");
  	             				return false;
  	             			}else{
  	             				if($scope.Selected.status_reservation.id==2 || $scope.Selected.status_reservation.id==1){
	   	             				alert("Phiếu không được phép xóa");
	   	             				return false;
  	             				}
  	             			}
  	             			$state.go('reservation.delete',$scope.Selected);
  	             		}
  	             	}
  	             	,'-',{
  	             		iconCls: 'icon-print',
  	             		handler: function(){
  	             			$state.go('reservation.printer',$scope.Selected);
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
