'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gender', {
                parent: 'entity',
                url: '/genders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Genders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gender/genders.html',
                        controller: 'GenderController'
                    }
                },
                resolve: {
                }
            })
            .state('gender.detail', {
                parent: 'entity',
                url: '/gender/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Gender'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gender/gender-detail.html',
                        controller: 'GenderDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Gender', function($stateParams, Gender) {
                        return Gender.get({id : $stateParams.id});
                    }]
                }
            })
            .state('gender.new', {
                parent: 'gender',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gender/gender-dialog.html',
                        controller: 'GenderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('gender', null, { reload: true });
                    }, function() {
                        $state.go('gender');
                    })
                }]
            })
            .state('gender.edit', {
                parent: 'gender',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gender/gender-dialog.html',
                        controller: 'GenderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Gender', function(Gender) {
                                return Gender.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gender', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('gender.delete', {
                parent: 'gender',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/gender/gender-delete-dialog.html',
                        controller: 'GenderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Gender', function(Gender) {
                                return Gender.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('gender', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
