package pereira.lopes.julio.halisson.galeirapublica;

public class GalleryRepository {
    Context context;

        public GalleryRepository(Context context) {
            this.context = context;
        }

        public List<ImageData> loadImageData(Integer limit, IntegeroffSet) throws FileNotFoundException {

            List<ImageData> imageDataList = new ArrayList<>();
            int w = (int) context.getResources().getDimension(R.dimen.im_width);
            int h = (int) context.getResources().getDimension(R.dimen.im_height);

        String[] projection = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.SIZE};
        String selection = null;
        String selectionArgs[] = null;
        String sort = MediaStore.Images.Media.DATE_ADDED;

        Cursor cursor = null;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            Bundle queryArgs = new Bundle();

            queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selection);
            queryArgs.putStringArray( contentResolver.QUERY_ARG_SQL_SELECTION_ARGS, selectionArgs);
            // sort
            32. queryArgs.putString(
                    33. ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    34. sort
            35. );
            36. queryArgs.putInt(
                    37. ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    38. ContentResolver.QUERY_SORT_DIRECTION_ASCENDING
            39. );
            40. // limit, offset
            41. queryArgs.putInt(ContentResolver.QUERY_ARG_LIMIT, limit);
            42. queryArgs.putInt(ContentResolver.QUERY_ARG_OFFSET,
                    offSet);
            43.
            44. cursor =
                    context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_C
                            ONTENT_URI,
                            45. projection,
                            46. queryArgs,
                            47. null
            48. );
            49.
            50. }
        51. else {
            52. cursor =
                    context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_C
                            ONTENT_URI,
                            53. projection,
                            54. selection,
                            55. selectionArgs,
                            56. sort + " ASC + LIMIT " + String.valueOf(limit) + "
                            OFFSET " + String.valueOf(offSet)
            57. );
            58. }
        59.
        60. int idColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        61. int nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        62. int dateAddedColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
        63. int sizeColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
        64.
        65. while (cursor.moveToNext()) {
            66. // Get values of columns for a given image.
            67. long id = cursor.getLong(idColumn);
            68. Uri contentUri = ContentUris.withAppendedId(
                    69. MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            70. String name = cursor.getString(nameColumn);
            71. int dateAdded = cursor.getInt(dateAddedColumn);
            72. int size = cursor.getInt(sizeColumn);
            73. Bitmap thumb = Util.getBitmap(context, contentUri, w, h);
            74.
            75. // Stores column values and the contentUri in a local
            object
            76. // that represents the media file.
            77. imageDataList.add(new ImageData(contentUri, thumb, name,
                    new Date(dateAdded*1000L), size));
            78. }
        79. return imageDataList;
        80. }
81.}
}
