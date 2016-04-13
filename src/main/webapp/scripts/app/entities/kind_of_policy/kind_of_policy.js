'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('kind_of_policy', {
                parent: 'app',
                url: '/kind_of_policys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Kind_of_policys'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/kind_of_policy/kind_of_policys.html',
                        controller: 'Kind_of_policyController'
                    }
                },
                resolve: {
                }
            })
            .state('kind_of_policy.detail', {
                parent: 'app',
                url: '/kind_of_policy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Kind_of_policy'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/kind_of_policy/kind_of_policy-detail.html',
                        controller: 'Kind_of_policyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Kind_of_policy', function($stateParams, Kind_of_policy) {
                        return Kind_of_policy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('kind_of_policy.new', {
                parent: 'kind_of_policy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/kind_of_policy/kind_of_policy-dialog.html',
                        controller: 'Kind_of_policyDialogController',
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
                        $state.go('kind_of_policy', null, { reload: true });
                    }, function() {
                        $state.go('kind_of_policy');
                    })
                }]
            })
            .state('kind_of_policy.edit', {
                parent: 'kind_of_policy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/kind_of_policy/kind_of_policy-dialog.html',
                        controller: 'Kind_of_policyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Kind_of_policy', function(Kind_of_policy) {
                                return Kind_of_policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('kind_of_policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('kind_of_policy.delete', {
                parent: 'kind_of_policy',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/kind_of_policy/kind_of_policy-delete-dialog.html',
                        controller: 'Kind_of_policyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Kind_of_policy', function(Kind_of_policy) {
                                return Kind_of_policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('kind_of_policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
