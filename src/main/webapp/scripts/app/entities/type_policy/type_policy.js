'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('type_policy', {
                parent: 'app',
                url: '/type_policys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Type_policys'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/type_policy/type_policys.html',
                        controller: 'Type_policyController'
                    }
                },
                resolve: {
                }
            })
            .state('type_policy.detail', {
                parent: 'app',
                url: '/type_policy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Type_policy'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/type_policy/type_policy-detail.html',
                        controller: 'Type_policyDetailController'
                    }
                },
                resolve: {
                    app: ['$stateParams', 'Type_policy', function($stateParams, Type_policy) {
                        return Type_policy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('type_policy.new', {
                parent: 'type_policy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/type_policy/type_policy-dialog.html',
                        controller: 'Type_policyDialogController',
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
                        $state.go('type_policy', null, { reload: true });
                    }, function() {
                        $state.go('type_policy');
                    })
                }]
            })
            .state('type_policy.edit', {
                parent: 'type_policy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/type_policy/type_policy-dialog.html',
                        controller: 'Type_policyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Type_policy', function(Type_policy) {
                                return Type_policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('type_policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('type_policy.delete', {
                parent: 'type_policy',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/type_policy/type_policy-delete-dialog.html',
                        controller: 'Type_policyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Type_policy', function(Type_policy) {
                                return Type_policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('type_policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
