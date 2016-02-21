'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employee', {
                parent: 'entity',
                url: '/employees',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Employees'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employee/employees.html',
                        controller: 'EmployeeController'
                    }
                },
                resolve: {
                }
            })
            .state('employee.detail', {
                parent: 'entity',
                url: '/employee/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Employee'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employee/employee-detail.html',
                        controller: 'EmployeeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        return Employee.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employee.new', {
                parent: 'employee',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/employee/employee-dialog.html',
                        controller: 'EmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    full_name: null,
                                    birthday: null,
                                    address: null,
                                    birthplace: null,
                                    perman_resid: null,
                                    father_name: null,
                                    mother_name: null,
                                    telephone: null,
                                    homephone: null,
                                    email: null,
                                    ic_number: null,
                                    ic_prov_date: null,
                                    ic_prov_add: null,
                                    bank_account: null,
                                    si_number: null,
                                    si_prov_date: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('employee', null, { reload: true });
                    }, function() {
                        $state.go('employee');
                    })
                }]
            })
            .state('employee.edit', {
                parent: 'employee',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/employee/employee-dialog.html',
                        controller: 'EmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Employee', function(Employee) {
                                return Employee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('employee.delete', {
                parent: 'employee',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/employee/employee-delete-dialog.html',
                        controller: 'EmployeeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Employee', function(Employee) {
                                return Employee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
