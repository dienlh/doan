'use strict';

angular
		.module('hotelApp')
		.factory(
				'Customer',
				function($resource, DateUtils) {
					return $resource(
							'api/customers/:id',
							{},
							{
								'query' : {
									method : 'GET',
									isArray : true
								},
								'findAllByIcPassportNumberAndEmail' : {
									method : 'GET',
									isArray : true,
									url : 'api/customers/findAllByIcPassportNumberAndEmail'
								},
								'get' : {
									method : 'GET',
									transformResponse : function(data) {
										data = angular.fromJson(data);
										data.ic_passport_prov_date = DateUtils
												.convertLocaleDateFromServer(data.ic_passport_prov_date);
										data.birthday = DateUtils
												.convertLocaleDateFromServer(data.birthday);
										data.create_date = DateUtils
												.convertDateTimeFromServer(data.create_date);
										data.last_modified_date = DateUtils
												.convertDateTimeFromServer(data.last_modified_date);
										return data;
									}
								},
								'update' : {
									method : 'PUT',
									transformRequest : function(data) {
										data.ic_passport_prov_date = DateUtils
												.convertLocaleDateToServer(data.ic_passport_prov_date);
										data.birthday = DateUtils
												.convertLocaleDateToServer(data.birthday);
										return angular.toJson(data);
									}
								},
								'save' : {
									method : 'POST',
									transformRequest : function(data) {
										data.ic_passport_prov_date = DateUtils
												.convertLocaleDateToServer(data.ic_passport_prov_date);
										data.birthday = DateUtils
												.convertLocaleDateToServer(data.birthday);
										return angular.toJson(data);
									}
								}
							});
				});
