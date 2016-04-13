'use strict';

angular.module('hotelApp').controller(
		'RoomController',
		function($scope, $state, Room, ParseLinks,Type_room,Status_room,$filter) {

			$scope.type_rooms = Type_room.query();
	        $scope.status_rooms = Status_room.query();
			$scope.rooms = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			$scope.size=10;
			$scope.code="";
			function checkNull(){
				if(!$scope.type_room){
					$scope.type_room={
							id:0
					}
				}
				if(!$scope.status_room){
					$scope.status_room={
							id:0
					}
				}
			}
			$scope.loadAll = function() {
				checkNull();
				Room.findAllByTypeAndStatus({
					code:$scope.code,
					type_room:$scope.type_room.id,
					status_room:$scope.status_room.id,
					page : $scope.page - 1,
					size : $scope.size,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.rooms = result;
					initDatagrid($scope.rooms);
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
				$scope.room = {
					code : null,
					key_code : null,
					title : null,
					is_pet : false,
					is_bed_kid : false,
					number_of_livingroom : null,
					number_of_bedroom : null,
					number_of_toilet : null,
					number_of_kitchen : null,
					number_of_bathroom : null,
					floor : null,
					orientation : null,
					surface_size : null,
					max_adults : null,
					max_kids : null,
					hourly_price : null,
					daily_price : null,
					monthly_price : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
			
			$scope.exportExcel = function(){
				checkNull();
				window.open("api/rooms/exportExcel?code="+$scope.code+
						"&type_room="+$scope.type_room.id+
						"&status_room="+$scope.status_room.id);
			}
			function initDatagrid(data){
	        	 $('#dg').datagrid({
	        		 data:data,
	// pagination:true,
	                 rownumbers:true,
	                 fitColumns:true,
	                 singleSelect:true,
	                 collapsible:true,
	                 width:"100%",
	                 title:"DANH SÁCH PHÒNG TÒA NHÀ DMCTOWER",
	                 nowrap:true,
	                 columns:[[
	                     {field:'code',title:'MP',width:50,fixed:true},
	                     {field:'key_code',title:'MK',width:50,fixed:true},
	                     {field:'is_pet',title:'TN',width:50,fixed:true,
	                    	 formatter: function(value,row,index){
	             				if (row.is_pet==true){
	             					return "<i class='fa fa-check icon text-success'></i>";
	             				} else {
	             					return "<i class='fa icon-close icon text-danger'></i>";
	             				}
	             			}
	                     },
	                     {field:'is_bed_kid',title:'TE',width:50,fixed:true,
	                    	 formatter: function(value,row,index){
	              				if (row.is_bed_kid==true){
	              					return "<i class='fa fa-check icon text-success'></i>";
	              				} else {
	              					return "<i class='fa icon-close icon text-danger'></i>";
	              				}
	              			}
	                     },
	                     {field:'number_of_livingroom',title:'PK',width:40,fixed:true},
	                     {field:'number_of_bedroom',title:'PN',width:40,fixed:true},
	                     {field:'number_of_toilet',title:'PVS',width:40,fixed:true},
	                     {field:'number_of_kitchen',title:'PB',width:40,fixed:true},
	                     {field:'number_of_bathroom',title:'PT',width:40,fixed:true},
	                     {field:'floor',title:'Tầng',width:100,fixed:true},
	                     {field:'surface_size',title:'DT',width:80,fixed:true},
	                     {field:'hourly_price',title:'Giá giờ',width:80,align:"right",fixed:true,
	                    	 formatter: function(value,row,index){
	              				return $filter('currency')(value,'',0)
	              			}
	                     },
	                     {field:'daily_price',title:'Giá ngày',width:80,align:"right",fixed:true,
	                    	 formatter: function(value,row,index){
	              				return $filter('currency')(value,'',0)
	              			}
	                     },
	                     {field:'monthly_price',title:'Giá tháng',width:80,align:"right",fixed:true,
	                    	 formatter: function(value,row,index){
	               				return $filter('currency')(value,'',0)
	               			}
	                     },
	                     {field:'currency',title:'DVT',width:50,fixed:true,
	                    	 formatter: function(value,row,index){
	                    		 if (value){
	              					return value.code;
	              				} else {
	              					return "";
	              				}
	                		}
	                     },
	                     {field:'type_room',title:'Loại phòng',width:130,fixed:true,
	                    	 formatter: function(value,row,index){
	                    		 if (value){
	              					return value.name;
	              				} else {
	              					return "";
	              				}
	                		}
	                     },
	                     {field:'status_room',title:'Trạng thái',width:130,fixed:true,
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
	                 	 $state.go('room.detail',row);
	                  },
	                  detailFormatter: function(rowIndex, rowData){
	                	return "help";
	                }
	             });
	             $('#dg').datagrid({
	             	toolbar: [{
		             		iconCls: 'icon-add',
		             		handler: function(){
		             			$state.go('room.new');
		             		}
		             	},'-',{
		             		iconCls: 'icon-edit',
		             		handler: function(){
		             			if(!$scope.Selected){
		             				alert("Bạn chưa chọn phòng!");
		             				return false;
		             			}
		             			$state.go('room.edit',$scope.Selected);
		             		}
		             	},'-',{
		             		iconCls: 'icon-remove',
		             		handler: function(){
		             			if(!$scope.Selected){
		             				alert("Bạn chưa chọn phòng!");
		             				return false;
		             			}
		             			$state.go('room.delete',$scope.Selected);
		             		}
		             	}
		             	,'-',{
		             		iconCls: 'icon-help',
		             		handler: function(){
		             			if(!$scope.Selected){
		             				alert("Bạn chưa chọn phòng!");
		             				return false;
		             			}
		             			$state.go('room.detail',$scope.Selected);
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
