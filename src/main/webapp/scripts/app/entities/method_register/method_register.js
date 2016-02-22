'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('method_register', {
                parent: 'entity',
                url: '/method_registers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Method_registers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/method_register/method_registers.html',
                        controller: 'Method_registerController'
                    }
                },
                resolve: {
                }
            })
            .state('method_register.detail', {
                parent: 'entity',
                url: '/method_register/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Method_register'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/method_register/method_register-detail.html',
                        controller: 'Method_registerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Method_register', function($stateParams, Method_register) {
                        return Method_register.get({id : $stateParams.id});
                    }]
                }
            })
            .state('method_register.new', {
                parent: 'method_register',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_register/method_register-dialog.html',
                        controller: 'Method_registerDialogController',
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
                        $state.go('method_register', null, { reload: true });
                    }, function() {
                        $state.go('method_register');
                    })
                }]
            })
            .state('method_register.edit', {
                parent: 'method_register',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_register/method_register-dialog.html',
                        controller: 'Method_registerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Method_register', function(Method_register) {
                                return Method_register.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('method_register', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('method_register.delete', {
                parent: 'method_register',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_register/method_register-delete-dialog.html',
                        controller: 'Method_registerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Method_register', function(Method_register) {
                                return Method_register.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('method_register', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
