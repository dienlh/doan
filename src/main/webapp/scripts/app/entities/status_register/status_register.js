'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_register', {
                parent: 'entity',
                url: '/status_registers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_registers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_register/status_registers.html',
                        controller: 'Status_registerController'
                    }
                },
                resolve: {
                }
            })
            .state('status_register.detail', {
                parent: 'entity',
                url: '/status_register/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_register'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_register/status_register-detail.html',
                        controller: 'Status_registerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_register', function($stateParams, Status_register) {
                        return Status_register.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_register.new', {
                parent: 'status_register',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_register/status_register-dialog.html',
                        controller: 'Status_registerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('status_register', null, { reload: true });
                    }, function() {
                        $state.go('status_register');
                    })
                }]
            })
            .state('status_register.edit', {
                parent: 'status_register',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_register/status_register-dialog.html',
                        controller: 'Status_registerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_register', function(Status_register) {
                                return Status_register.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_register', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_register.delete', {
                parent: 'status_register',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_register/status_register-delete-dialog.html',
                        controller: 'Status_registerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_register', function(Status_register) {
                                return Status_register.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_register', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
