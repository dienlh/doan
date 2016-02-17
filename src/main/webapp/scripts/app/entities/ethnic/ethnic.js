'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ethnic', {
                parent: 'entity',
                url: '/ethnics',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Ethnics'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ethnic/ethnics.html',
                        controller: 'EthnicController'
                    }
                },
                resolve: {
                }
            })
            .state('ethnic.detail', {
                parent: 'entity',
                url: '/ethnic/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Ethnic'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ethnic/ethnic-detail.html',
                        controller: 'EthnicDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Ethnic', function($stateParams, Ethnic) {
                        return Ethnic.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ethnic.new', {
                parent: 'ethnic',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ethnic/ethnic-dialog.html',
                        controller: 'EthnicDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ethnic', null, { reload: true });
                    }, function() {
                        $state.go('ethnic');
                    })
                }]
            })
            .state('ethnic.edit', {
                parent: 'ethnic',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ethnic/ethnic-dialog.html',
                        controller: 'EthnicDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ethnic', function(Ethnic) {
                                return Ethnic.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ethnic', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('ethnic.delete', {
                parent: 'ethnic',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/ethnic/ethnic-delete-dialog.html',
                        controller: 'EthnicDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Ethnic', function(Ethnic) {
                                return Ethnic.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ethnic', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
