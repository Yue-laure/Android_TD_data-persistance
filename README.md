[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/iO5XMYt2)

## Ce que j ai fait

Dans ce projet, je montre qu'un clic sur une image peut être multisélectionné, 
et qu'un appui long peut sélectionner l'image actuelle pour une prévisualisation en plein écran, 
le partage de l'image actuelle, et la suppression de l'image actuelle. 
J'ai également ajouté un bouton photo. 
Toutes les fonctionnalités sont présentées dans la vidéo LOOKME !

# Architecture Components and Retrofit

Dans ce TP, vous allez apprendre à faire un appel API avec Retrofit et à afficher les données dans
une RecyclerView.

## Configuration

- Cloner le projet et compilez-le. Il se peut que vous ayez à modifier les versions de gradle en
  fonction de la version d'Android Studio que vous utilisez.
- Prendre le temps d'analyser les codes, il ya quelques TODO à traiter.

## Améliorer l'app

- Ajouter une option permettant de supprimer 1 ou plusieurs photos de la liste. Par exemple, quand
  l'utilisateur fait un appui long sur une photo, vous pouvez faire passer la liste en mode
  selectionnable. L'utilisateur peut alors sélectionner plusieurs photos et les supprimer en
  cliquant sur un bouton. L'API ne supportant pas la suppression, vous pouvez le faire sur la liste
  des objets en mémoire.
- Ajouter une option permettant de partager une photo. 
- Ajouter une vue détail (i.e un fragment). Quand l'utilisateur clique sur une photo, il doit voir la photo en plein écran.

## Mettre en place une base de données

- Ajouter une base de données Room à votre projet. Quand les photos sont récupérées depuis l'API,
  vous les sauvegardez dans une base de données locales afin de permettre l'utilisation de l'
  application en mode Offline. Ainsi, au lancement de l'application, si l'utilisateur n'est pas
  connecté à internet; vous affichez les photos sauvegardées dans la base de données locales.
- Modifiez la fonction de suppression pour supprimer les photos dans la base de données locales et
  non plus dans la liste en mémoire. Attention, quand vous récupérez la liste des photos depuis l'API, il faut ignorer celles qui auront été
  supprimées par l'utilisateur. 

## Fonctionnalités au choix (1 dans la liste)

- Possibilité de prendre une photo ou d'en selectionner depuis la gallerie et l'enregistrer dans la
  base de données
- Enrichir les données et la vue :
  - Mécanisme permettant d'indiquer qu'ne photo a été supprimée.
  - Mécanisme permettant d'indiquer qu'une photo a été partagée;
  - Mécanisme permettant d'indiquer qu'une photo a été visualisée;
  - Mécanisem permettant d'indiquer qu'une photo a été likée;
- Ajouter une vue gallerie permettant de swiper pour passer à la photo suivante ou précédente.

## Bonus
- 2+ fonctionnalités parmi la liste des fonctionnalités au choix

## Rendu
- Ajouter une fichier (RELEASE.md) dans lequel vous spécifierez :
    - La liste des fonctionnalités de l'application developpée (captures d'écran des vues assiciées)
    - La liste des fonctionnalités bonus
    - Les membres du groupe et les fonctionnalités qu'ils ont développées
