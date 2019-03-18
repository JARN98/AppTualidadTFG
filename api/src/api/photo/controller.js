import { success, notFound } from '../../services/response/'
import { Photo } from '.'
import { Noticia } from '../noticia'
const uploadService = require('../../services/upload/')

export const create = async (req, res, next) => {
  console.log(req.file);
  
  var idNoticia
  var link
  var id
  await uploadService.uploadFromBinary(req.file.buffer)
    .then(json => Photo.create({
      noticia: req.body.noticia,
      imgurLink: json.data.link,
      deletehash: json.data.deletehash
    }))
    .then((photo) => {
      idNoticia = photo.noticia
      link = photo.imgurLink
      id = photo.id

      return photo.view(true)
    })
    .then(success(res, 201))
    .catch(next)

  await Noticia.findOne(idNoticia)
    .then(noticia => {
      console.log(noticia.photos)
      noticia.photos.push({id, idNoticia, link})
      noticia.save()
    })
    .catch(next)
}
// Photo.create(body)
//   .then((photo) => photo.view(true))
//   .then(success(res, 201))
//   .catch(next)

export const index = ({ querymen: { query, select, cursor } }, res, next) =>
  Photo.count(query)
    .then(count => Photo.find(query, select, cursor)
      .then((photos) => ({
        count,
        rows: photos.map((photo) => photo.view())
      }))
    )
    .then(success(res))
    .catch(next)

export const show = ({ params }, res, next) =>
  Photo.findById(params.id)
    .then(notFound(res))
    .then((photo) => photo ? photo.view() : null)
    .then(success(res))
    .catch(next)

export const update = ({ bodymen: { body }, params }, res, next) =>
  Photo.findById(params.id)
    .then(notFound(res))
    .then((photo) => photo ? Object.assign(photo, body).save() : null)
    .then((photo) => photo ? photo.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = ({ params }, res, next) => 
  Photo.findById(params.id)
    .then(notFound(res))
    .then((photo) => photo ? photo.remove() : null)
    .then(success(res, 204))
    .catch(next)
