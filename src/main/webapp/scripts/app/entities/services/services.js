'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('services', {
                parent: 'entity',
                url: '/servicess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Servicess'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/services/servicess.html',
                        controller: 'ServicesController'
                    }
                },
                resolve: {
                }
            })
            .state('services.detail', {
                parent: 'entity',
                url: '/services/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Services'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/services/services-detail.html',
                        controller: 'ServicesDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Services', function($stateParams, Services) {
                        return Services.get({id : $stateParams.id});
                    }]
                }
            })
            .state('services.new', {
                parent: 'services',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/services/services-dialog.html',
                        controller: 'ServicesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    price: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('services', null, { reload: true });
                    }, function() {
                        $state.go('services');
                    })
                }]
            })
            .state('services.edit', {
                parent: 'services',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/services/services-dialog.html',
                        controller: 'ServicesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Services', function(Services) {
                                return Services.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('services', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('services.delete', {
                parent: 'services',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/services/services-delete-dialog.html',
                        controller: 'ServicesDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Services', function(Services) {
                                return Services.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('services', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
