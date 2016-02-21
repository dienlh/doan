'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('policy', {
                parent: 'entity',
                url: '/policys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Policys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/policy/policys.html',
                        controller: 'PolicyController'
                    }
                },
                resolve: {
                }
            })
            .state('policy.detail', {
                parent: 'entity',
                url: '/policy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Policy'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/policy/policy-detail.html',
                        controller: 'PolicyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Policy', function($stateParams, Policy) {
                        return Policy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('policy.new', {
                parent: 'policy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/policy/policy-dialog.html',
                        controller: 'PolicyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    decription: null,
                                    start_date: null,
                                    end_date: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('policy', null, { reload: true });
                    }, function() {
                        $state.go('policy');
                    })
                }]
            })
            .state('policy.edit', {
                parent: 'policy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/policy/policy-dialog.html',
                        controller: 'PolicyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Policy', function(Policy) {
                                return Policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('policy.delete', {
                parent: 'policy',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/policy/policy-delete-dialog.html',
                        controller: 'PolicyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Policy', function(Policy) {
                                return Policy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('policy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
